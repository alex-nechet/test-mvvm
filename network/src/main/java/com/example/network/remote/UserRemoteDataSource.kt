package com.example.network.remote

import com.example.network.GitApi
import com.example.network.ResponseConverter
import com.example.network.model.UserResponse

interface UserRemoteDataSource {
    suspend fun getUsers(page: Long): Result<List<UserResponse>?>
    suspend fun getDetails(userId: Long): Result<UserResponse?>
}

class UserRemoteDataSourceImpl(
    private val api: GitApi,
    private val responseConverter: ResponseConverter
) : UserRemoteDataSource {
    override suspend fun getUsers(page: Long) = responseConverter.toResult {
        api.getUsers(page)
    }

    override suspend fun getDetails(userId: Long) =
        responseConverter.toResult { api.getDetails(userId) }
}