package com.example.domain


import com.example.domain.model.ErrorType
import com.example.domain.model.OtherInfo
import com.example.domain.model.State
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

interface UserDetailsInteractor {
    fun getUserDetails(userId: Long): Flow<State<User>>
}

class UserDetailsInteractorImpl(
    private val repository: UserRepository,
    private val coroutineContext: CoroutineContext
) : UserDetailsInteractor {

    override fun getUserDetails(userId: Long): Flow<State<User>> = flow {
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