package com.example.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.domain.model.BriefInfo
import com.example.domain.model.OtherInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    @ExperimentalPagingApi
    fun fetchUsers(): Flow<PagingData<BriefInfo>>

    suspend fun fetchBriefDetails(userId: Long): BriefInfo

    suspend fun fetchUser(userId: Long): Result<OtherInfo?>
}