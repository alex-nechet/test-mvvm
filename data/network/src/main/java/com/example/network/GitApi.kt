package com.example.network

import com.example.network.dto.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitApi {
    @GET("users")
    suspend fun getUsers(@Query("since") lastUserId: Long): Response<List<UserResponse>>

    @GET("users/{userid}")
    suspend fun getDetails(@Path("userid") userId: Long): Response<UserResponse>
}
