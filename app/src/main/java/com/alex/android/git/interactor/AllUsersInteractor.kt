package com.alex.android.git.interactor

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.alex.android.git.interactor.model.User
import com.alex.android.git.interactor.model.toUser
import com.example.data.db.model.UserDb
import com.example.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

interface AllUsersInteractor {
    fun getUsers(): Flow<PagingData<User>>
}

class AllUsersInteractorImpl(private val repository: UserRepository) : AllUsersInteractor {

    private val coroutineContext = Dispatchers.IO

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsers(): Flow<PagingData<User>> =
        repository.fetchUsers().flow.map { pagingData ->
            pagingData.map { it.toUser() }
        }.flowOn(coroutineContext)
}