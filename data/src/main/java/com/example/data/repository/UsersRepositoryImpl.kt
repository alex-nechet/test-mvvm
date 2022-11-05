package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.converters.toBriefInfo
import com.example.data.converters.toOtherInfo
import com.example.data.db.AppDatabase
import com.example.domain.model.BriefInfo
import com.example.domain.repository.UserRepository
import com.example.network.ApiProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

private const val PAGE_SIZE = 30

class UsersRepositoryImpl(
    private val remote: ApiProvider,
    private val local: AppDatabase,
    private val coroutineContext: CoroutineContext
) : UserRepository {

    @ExperimentalPagingApi
    override fun fetchUsers(): Flow<PagingData<BriefInfo>> {
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
        ).flow.map { pd-> pd.map { it.toBriefInfo() } }.flowOn(coroutineContext)
    }

    override fun fetchBriefDetails(userId: Long) = local.usersDao().getUser(userId)
        .map { it.toBriefInfo() }
        .flowOn(coroutineContext)

    override suspend fun fetchUser(userId: Long) = remote.getDetails(userId).map { it?.toOtherInfo() }

}

