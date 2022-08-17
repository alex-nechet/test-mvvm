package com.alex.android.git.data

import com.alex.android.git.BuildConfig
import com.alex.android.git.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitApi {
    @GET("users")
    suspend fun getUsers(
        @Query("since") lastUserId: Long
    ): List<User>


    @GET("users/{userid}")
    suspend fun getDetails(
        @Path("userid") userId: Long
    ): Response<User>
}
