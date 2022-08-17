package com.alex.android.git.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alex.android.git.data.model.User
import com.alex.android.git.data.model.UserDb
import com.alex.android.git.network.ApiProvider
import com.alex.android.git.network.ResponseConverter
import com.alex.android.git.network.Result
import com.alex.android.git.repository.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

private const val PAGE_SIZE = 30

class UsersRepository(
    private val apiProvider: ApiProvider,
    private val responseConverter: ResponseConverter,
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

    suspend fun fetchDetails(userId: Long) = flow {
        emit(Result.Loading())
        try {
            val result: UserDb = appDatabase.usersDao().getUser(userId)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error<UserDb>(e.message))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun fetchUser(userId: Long) = flow {
        emit(Result.Loading())
        try {
            val result: Result<User> = responseConverter.toResult {  apiProvider.api.getDetails(userId)}
            emit(result)
        } catch (e: Exception) {
            emit(Result.Error<User>(e.message))
        }
    }.flowOn(Dispatchers.IO)
}

