package com.alex.android.git.interactor

import com.alex.android.git.interactor.converters.toBriefInfo
import com.alex.android.git.interactor.converters.toOtherInfo
import com.alex.android.git.interactor.model.BriefInfo
import com.alex.android.git.interactor.model.OtherInfo
import com.alex.android.git.interactor.model.State
import com.example.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

interface UserDetailsIteractor {

    fun getBriefUserDetails(userId: Long): Flow<BriefInfo>

    fun getAdvancedUserDetails(userId: Long): Flow<State<OtherInfo>>
}

class UserDetailsInteractorImpl(private val repository: UserRepository) : UserDetailsIteractor{

    private val coroutineContext = Dispatchers.IO

    override fun getBriefUserDetails(userId: Long) =
        repository.fetchDetails(userId).map { it.toBriefInfo() }.flowOn(coroutineContext)

    override fun getAdvancedUserDetails(userId: Long): Flow<State<OtherInfo>> = flow {
        emit(State.Loading())
        val result = repository.fetchUser(userId).map { it?.toOtherInfo() }
        result.onFailure { emit(State.Error<OtherInfo>(it.message)) }
            .onSuccess { emit(State.Success(it)) }
    }.flowOn(coroutineContext)

}