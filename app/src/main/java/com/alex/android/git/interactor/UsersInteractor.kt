package com.alex.android.git.interactor

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.alex.android.git.interactor.model.BriefInfo
import com.alex.android.git.interactor.model.OtherInfo
import com.alex.android.git.interactor.converters.toBriefInfo
import com.alex.android.git.interactor.converters.toOtherInfo
import com.example.data.db.model.UserDb
import com.example.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

interface UsersInteractor {
    @ExperimentalPagingApi
    fun getUsers(): Flow<PagingData<UserDb>>

    fun getBriefUserDetails(userId: Long): Flow<BriefInfo>

    fun getAdvancedUserDetails(userId: Long): Flow<State<OtherInfo>>
}

class UsersInteractorImpl(private val repository: UserRepository) : UsersInteractor {

    private val coroutineContext = Dispatchers.IO

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsers() = repository.fetchUsers().flow.flowOn(coroutineContext)

    override fun getBriefUserDetails(userId: Long) =
        repository.fetchDetails(userId).map { it.toBriefInfo() }.flowOn(coroutineContext)

    override fun getAdvancedUserDetails(userId: Long): Flow<State<OtherInfo>> = flow<State<OtherInfo>> {
        emit(State.Loading())
        val result = repository.fetchUser(userId).map { it?.toOtherInfo() }
        result.onFailure { emit(State.Error<OtherInfo>(it.message)) }
            .onSuccess { emit(State.Success(it)) }
    }.flowOn(coroutineContext)


}