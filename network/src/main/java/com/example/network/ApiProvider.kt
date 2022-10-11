package com.example.network

import retrofit2.Retrofit

class ApiProvider(
    private val retrofit: Retrofit,
    private val responseConverter: ResponseConverter
) {

    private val api: GitApi = retrofit.create(GitApi::class.java)

    suspend fun getUsers(page: Long) = api.getUsers(page)

    suspend fun getDetails(userId: Long) = responseConverter.toResult { api.getDetails(userId) }
}