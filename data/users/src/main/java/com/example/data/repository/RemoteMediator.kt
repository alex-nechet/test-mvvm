package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.db.model.UserDb
import com.example.data.mappers.toDb
import com.example.network.remote.UserRemoteDataSource

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
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as Long
        }

        return prepareMediatorResult(page, loadType)
    }

    private suspend fun prepareMediatorResult(
        page: Long,
        loadType: LoadType
    ): MediatorResult {
        val response = userRemoteDataSource.getUsers(page)
        var result: MediatorResult = MediatorResult.Error(Throwable("No result"))
        response.onSuccess {
            val res = it ?: emptyList()
            val isEndOfList = res.isEmpty()
            if (loadType == LoadType.REFRESH) {
                userLocalDataSource.deleteAll()
            }
            userLocalDataSource.insertAll(res.map { user -> user.toDb() })
            result = MediatorResult.Success(endOfPaginationReached = isEndOfList)
        }
            .onFailure {
                result = MediatorResult.Error(it)
            }
        return result
    }

    private fun getKeyPageData(loadType: LoadType, state: PagingState<Int, UserDb>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.minus(1) ?: START_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                remoteKeys
                    ?: return MediatorResult.Success(endOfPaginationReached = true)

            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                remoteKeys
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }

    private fun getLastRemoteKey(state: PagingState<Int, UserDb>) =
        state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.id

    private fun getFirstRemoteKey(state: PagingState<Int, UserDb>) =
        state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.id

    private fun getClosestRemoteKey(state: PagingState<Int, UserDb>) =
        state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id
        }
}