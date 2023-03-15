package com.example.domain


import com.example.domain.common.model.ErrorType
import com.example.domain.common.model.State
import com.example.domain.entity.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class GetUserDetailsUseCase(
    private val repository: UserRepository,
    private val coroutineContext: CoroutineContext
) {

    operator fun invoke(userId: Long): Flow<State<User>> = flow {
        emit(State.Loading())
        val result = repository.fetchUser(userId)
        result.onFailure { emit(State.Error<User>(ErrorType.FETCHING_ERROR)) }
            .onSuccess { user ->
                when (user) {
                    null -> emit(State.Error<User>(ErrorType.FETCHING_ERROR))
                    else -> emit(State.Success(user))
                }
            }
    }.flowOn(coroutineContext)

}