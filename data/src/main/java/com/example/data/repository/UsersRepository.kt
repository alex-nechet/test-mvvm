package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.RemoteMediator
import com.example.data.db.AppDatabase
import com.example.data.db.model.UserDb
import com.example.network.ApiProvider
import com.example.network.model.UserResponse
import kotlinx.coroutines.flow.Flow


private const val PAGE_SIZE = 30

interface UserRepository {
    @ExperimentalPagingApi
    fun fetchUsers(): Pager<Int, UserDb>

    fun fetchDetails(userId: Long): Flow<UserDb>

    suspend fun fetchUser(userId: Long): Result<UserResponse?>
}

class UsersRepositoryImpl(
    private val remote: ApiProvider,
    private val local: AppDatabase
) : UserRepository {

    @ExperimentalPagingApi
    override fun fetchUsers(): Pager<Int, UserDb> {
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
        )
    }

    override fun fetchDetails(userId: Long) = local.usersDao().getUser(userId)

    override suspend fun fetchUser(userId: Long) = remote.getDetails(userId)

}

