package com.example.users.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.network.model.UserResponse
import com.example.network.remote.UserRemoteDataSource
import com.example.users.datasource.local.UserLocalDataSource
import com.example.users.db.model.UserDb
import com.example.users.mappers.toDb

private const val START_PAGE_INDEX = 0L

@ExperimentalPagingApi
internal class RemoteMediator(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : RemoteMediator<Int, UserDb>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserDb>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> START_PAGE_INDEX
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.id ?: START_PAGE_INDEX
            }

            if (loadKey == START_PAGE_INDEX) {
                userLocalDataSource.deleteAll()
            }

            val response = userRemoteDataSource.getUsers(loadKey)

            response.onSuccess { list ->
                list?.let { nonNullList ->
                    userLocalDataSource.insertAll(nonNullList.map { it.toDb() })
                }
            }
            return mediatorResult(response)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun mediatorResult(response: Result<List<UserResponse>?>) = response.fold(
        onSuccess = { MediatorResult.Success(it.isNullOrEmpty()) },
        onFailure = { MediatorResult.Error(it) }
    )
}