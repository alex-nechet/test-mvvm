package com.example.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.map
import com.alex.android.git.interactor.model.User
import com.alex.android.git.interactor.model.toUser
import com.example.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

interface AllUsersInteractor {
    fun getUsers(): Flow<PagingData<User>>
}

class AllUsersInteractorImpl(private val repository: UserRepository) : AllUsersInteractor {

    private val coroutineContext = Dispatchers.IO

    @ExperimentalPagingApi
    override fun getUsers(): Flow<PagingData<User>> =
        repository.fetchUsers().flow.map { pagingData ->
            pagingData.map { it.toUser() }
        }.flowOn(coroutineContext)
}