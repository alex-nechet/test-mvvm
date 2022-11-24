package com.example.domain


import com.example.domain.model.BriefInfo
import com.example.domain.model.OtherInfo
import com.alex.android.git.interactor.model.State
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

interface UserDetailsInteractor {

    suspend fun getBriefUserDetails(userId: Long): BriefInfo

    fun getAdvancedUserDetails(userId: Long): Flow<State<OtherInfo>>
}

class UserDetailsInteractorImpl(
    private val repository: UserRepository,
    private val coroutineContext: CoroutineContext
) : UserDetailsInteractor {

    override suspend fun getBriefUserDetails(userId: Long): BriefInfo =
        repository.fetchBriefDetails(userId)

    override fun getAdvancedUserDetails(userId: Long): Flow<State<OtherInfo>> = flow {
        emit(State.Loading())
        val result = repository.fetchUser(userId)
        result.onFailure { emit(State.Error<OtherInfo>(it.message)) }
            .onSuccess { emit(State.Success(it)) }
    }.flowOn(coroutineContext)

}