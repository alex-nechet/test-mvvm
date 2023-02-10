package com.example.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    @ExperimentalPagingApi
    fun fetchUsers(): Flow<PagingData<User>>
    suspend fun fetchUser(userId: Long): Result<User?>
}