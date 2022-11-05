package com.example.domain

import androidx.paging.ExperimentalPagingApi
import com.example.domain.repository.UserRepository

@OptIn(ExperimentalPagingApi::class)
class AllUsersInteractor(private val repository: UserRepository) {

    operator fun invoke() = repository.fetchUsers()
}