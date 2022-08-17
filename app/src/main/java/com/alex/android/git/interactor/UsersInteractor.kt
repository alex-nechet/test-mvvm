package com.alex.android.git.interactor

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.alex.android.git.data.converters.toBriefInfo
import com.alex.android.git.data.converters.toOtherInfo
import com.alex.android.git.data.model.BriefInfo
import com.alex.android.git.data.model.OtherInfo
import com.alex.android.git.data.model.User
import com.alex.android.git.data.model.UserDb
import com.alex.android.git.network.Result
import com.alex.android.git.repository.UsersRepository
import kotlinx.coroutines.flow.*

interface UsersInteractor {
    @ExperimentalPagingApi
    fun getUsers(): Flow<PagingData<UserDb>>

    suspend fun getBriefUserDetails(userId: Long): Flow<Result<BriefInfo>>

    suspend fun getAdvancedUserDetails(userId: Long): Flow<Result<OtherInfo>>
}

class UsersInteractorImpl(private val repository: UsersRepository) : UsersInteractor {
    @ExperimentalPagingApi
    override fun getUsers() = repository.fetchUsers()

    override suspend fun getBriefUserDetails(userId: Long) =
        repository.fetchDetails(userId).map { result ->
            when (result) {
                is Result.Success -> Result.Success(result.data?.toBriefInfo())
                is Result.Error -> Result.Error(result.msg, result.cause)
                is Result.Loading -> Result.Loading()
            }
        }

    override suspend fun getAdvancedUserDetails(userId: Long) =
        repository.fetchUser(userId).map { result ->
            when (result) {
                is Result.Success -> Result.Success(result.data?.toOtherInfo())
                is Result.Error -> Result.Error(result.msg, result.cause)
                is Result.Loading -> Result.Loading()
            }
        }

}