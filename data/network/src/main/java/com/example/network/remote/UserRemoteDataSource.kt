package com.example.network.remote

import com.example.network.GitApi
import com.example.network.mappers.toResult
import com.example.network.dto.UserResponse
import retrofit2.Retrofit

interface UserRemoteDataSource {
    suspend fun getUsers(page: Long): Result<List<UserResponse>?>
    suspend fun getDetails(userId: Long): Result<UserResponse?>
}

internal class UserRemoteDataSourceImpl(private val retrofit: Retrofit) : UserRemoteDataSource {
    private val api = retrofit.create(GitApi::class.java)

    override suspend fun getUsers(page: Long) = api.getUsers(page).toResult(retrofit)

    override suspend fun getDetails(userId: Long) = api.getDetails(userId).toResult(retrofit)
}