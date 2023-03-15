package com.example.domain

import com.example.domain.common.model.ErrorType
import com.example.domain.common.model.State
import com.example.domain.entity.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserDetailsUseCase(
    private val repository: UserRepository
) {

    operator fun invoke(userId: Long): Flow<State<User>> = repository.fetchUser(userId).map {
        return@map it.fold(
            onSuccess = { user ->
                when (user) {
                    null -> State.Error<User>(ErrorType.FETCHING_ERROR)
                    else -> State.Success(user)
                }
            },
            onFailure = { State.Error<User>(ErrorType.FETCHING_ERROR) }
        )
    }
}
