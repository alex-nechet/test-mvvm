package com.example.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.domain.entity.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUsersUseCase(private val repository: UserRepository) {
    operator fun invoke(): Flow<PagingData<User>> = repository.fetchUsers()

}