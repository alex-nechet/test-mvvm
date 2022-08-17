package com.alex.android.git.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.alex.android.git.data.converters.toDb
import com.alex.android.git.data.model.UserDb
import com.alex.android.git.network.ApiProvider
import com.alex.android.git.repository.db.AppDatabase
import retrofit2.HttpException
import java.io.IOException
import java.util.*

private const val START_PAGE_INDEX = 0L

@ExperimentalPagingApi
class RemoteMediator(
    private val apiProvider: ApiProvider,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, UserDb>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserDb>
    ): MediatorResult {

        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as Long
        }

        try {
            val response = apiProvider.api.getUsers(page)
            val isEndOfList = response.isNullOrEmpty()
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.usersDao().deleteAll()
                }

                appDatabase.usersDao().insertAll(response.map { user -> user.toDb() })
            }

            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
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
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                remoteKeys
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
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