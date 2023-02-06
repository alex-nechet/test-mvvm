package com.example.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class GetAllUsersUseCase(private val repository: UserRepository) {

    operator fun invoke(): Flow<PagingData<User>> = repository.fetchUsers()

}