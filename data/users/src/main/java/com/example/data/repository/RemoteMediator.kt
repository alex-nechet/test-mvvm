package com.example.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.db.model.UserDb
import com.example.data.mappers.toDb
import com.example.network.model.UserResponse
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
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.id
            }

            val response = userRemoteDataSource.getUsers(loadKey ?: START_PAGE_INDEX)
            Log.e("LOADKEY", loadKey.toString())
            if (loadType == LoadType.REFRESH) {
                userLocalDataSource.deleteAll()
            }
            return mediatorResult(response)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun mediatorResult(response: Result<List<UserResponse>?>): MediatorResult {
        var result: MediatorResult = MediatorResult.Error(Exception("Unknown Error"))
        response
            .onSuccess { list ->
                list?.let { nonNullList ->
                    userLocalDataSource.insertAll(nonNullList.map { it.toDb() })
                }
                result = MediatorResult.Success(list.isNullOrEmpty())
            }
            .onFailure { result = MediatorResult.Error(it) }
        return result
    }
}