package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.converters.toUserData
import com.example.data.db.AppDatabase
import com.example.data.repository.model.UserData
import com.example.data.db.model.UserDb
import com.example.network.ApiProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

private const val PAGE_SIZE = 30

interface UserRepository {
    @ExperimentalPagingApi
    fun fetchUsers(): Flow<PagingData<UserDb>>

    fun fetchDetails(userId: Long): Flow<UserDb>

    suspend fun fetchUser(userId: Long): Result<UserData?>
}

class UsersRepositoryImpl(
    private val remote: ApiProvider,
    private val local: AppDatabase,
    private val coroutineContext: CoroutineContext
) : UserRepository {

    @ExperimentalPagingApi
    override fun fetchUsers(): Flow<PagingData<UserDb>> {
        val pagingSourceFactory = {
            local.usersDao().getAll()
                ?: throw IllegalStateException("Database is not initialized")
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = RemoteMediator(remote, local)
        ).flow.flowOn(coroutineContext)
    }

    override fun fetchDetails(userId: Long) = local.usersDao().getUser(userId).flowOn(coroutineContext)

    override suspend fun fetchUser(userId: Long) = remote.getDetails(userId).map { it?.toUserData() }

}

