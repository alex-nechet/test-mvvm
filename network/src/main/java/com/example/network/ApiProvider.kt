package com.example.network

class ApiProvider(
    private val api: GitApi ,
    private val responseConverter: ResponseConverter
) {
    suspend fun getUsers(page: Long) = api.getUsers(page)

    suspend fun getDetails(userId: Long) = responseConverter.toResult { api.getDetails(userId) }
}