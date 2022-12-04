package com.example.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.map
import com.example.domain.model.UserBaseInfo
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class AllUsersUseCase(private val repository: UserRepository) {

    operator fun invoke(): Flow<PagingData<UserBaseInfo>> = repository.fetchUsers().map { list -> list.map { it } }

}