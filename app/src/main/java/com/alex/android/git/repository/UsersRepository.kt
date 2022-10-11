package com.alex.android.git.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alex.android.git.data.model.UserDb
import com.example.network.Result
import com.alex.android.git.repository.db.AppDatabase
import com.example.network.ApiProvider
import com.example.network.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

private const val PAGE_SIZE = 30

class UsersRepository(
    private val apiProvider: ApiProvider,
    private val appDatabase: AppDatabase
) {

    @ExperimentalPagingApi
    fun fetchUsers(): Flow<PagingData<UserDb>> {
        val pagingSourceFactory = {
            appDatabase.usersDao().getAll()
                ?: throw IllegalStateException("Database is not initialized")
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = RemoteMediator(apiProvider, appDatabase)
        ).flow.flowOn(Dispatchers.IO)
    }

     fun fetchDetails(userId: Long): Flow<Result<UserDb>> = flow {
        emit(Result.Loading<UserDb>())
        try {
            val result: UserDb = appDatabase.usersDao().getUser(userId)
            emit(Result.Success<UserDb>(result))
        } catch (e: Exception) {
            emit(Result.Error<UserDb>(e.message))
        }
    }.flowOn(Dispatchers.IO)

     fun fetchUser(userId: Long) = flow {
        emit(Result.Loading<UserResponse>())
        try {
            val result: Result<UserResponse> = apiProvider.getDetails(userId)
            emit(result)
        } catch (e: Exception) {
            emit(Result.Error<UserResponse>(e.message))
        }
    }.flowOn(Dispatchers.IO)
}

