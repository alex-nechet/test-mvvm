package com.example.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import com.example.domain.model.BriefInfo
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class AllUsersInteractor(private val repository: UserRepository) {

    operator fun invoke() = repository.fetchUsers().map { list -> list.map { it.toBriefInfo() } }

    private fun User.toBriefInfo() = BriefInfo(
        id = id,
        name = name,
        login = login,
        avatarUrl = avatarUrl,
        url = url
    )

}