package com.alex.android.git.network

import com.alex.android.git.data.GitApi
import retrofit2.Retrofit

class ApiProvider(private val retrofit: Retrofit) {
    val api: GitApi = retrofit.create(GitApi::class.java)
}