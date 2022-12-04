package com.example.users.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.users.datasource.local.UserLocalDataSource
import com.example.users.mappers.toDb
import com.example.users.mappers.toUser
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import com.example.network.remote.UserRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

private const val PAGE_SIZE = 50

class UsersRepositoryImpl(
    private val remote: UserRemoteDataSource,
    private val local: UserLocalDataSource,
    private val coroutineContext: CoroutineContext
) : UserRepository {

    @ExperimentalPagingApi
    override fun fetchUsers(): Flow<PagingData<User>> {
        val pagingSourceFactory = {
            local.getAllUsers()
                ?: throw IllegalStateException("Database is not initialized")
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance =  PAGE_SIZE + PAGE_SIZE
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = RemoteMediator(remote, local)
        ).flow.map { pd ->
            pd.map { it.toUser() }
        }.flowOn(coroutineContext)
    }

    override suspend fun fetchUser(userId: Long): Result<User?> = withContext(coroutineContext) {
        when (val localUser = local.getUserDetails(userId)) {
            null -> {
                val remoteUser = remote.getDetails(userId)
                remoteUser.onSuccess { user -> user?.let { local.insertAll(listOf(it.toDb())) } }
                remoteUser.map { it?.toUser() }
            }
            else -> Result.success(localUser.toUser())
        }
    }

}

