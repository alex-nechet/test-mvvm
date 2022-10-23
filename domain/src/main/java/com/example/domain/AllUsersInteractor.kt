package com.example.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import com.alex.android.git.interactor.model.toUser
import com.example.data.repository.UserRepository
import kotlinx.coroutines.flow.map


@OptIn(ExperimentalPagingApi::class)
class AllUsersInteractor(private val repository: UserRepository) {

    operator fun invoke() = repository.fetchUsers().map { pagingData ->
        pagingData.map { it.toUser() }
    }
}