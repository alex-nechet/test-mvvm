package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun fetchUsers(): Flow<PagingData<User>>

    fun fetchUser(userId: Long): Flow<Result<User?>>
}
