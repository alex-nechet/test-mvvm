package com.example.network.mappers

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException


fun <T> Response<T>.toResult(retrofit: Retrofit): Result<T?> {
    return when {
        this.isSuccessful -> Result.success(this.body())
        else -> {
            val errorMessage = parseError(this, retrofit)
            Result.failure(Throwable("Code ${this.code()}\n$errorMessage"))}
    }
}

private fun parseError(response: Response<*>, retrofit: Retrofit): String {
    val converter = retrofit.responseBodyConverter<Error>(
        Error::class.java,
        arrayOfNulls(0)
    )
    return try {
        response.errorBody()?.let { responseBody ->
            converter.convert(responseBody)?.message.orEmpty()
        }.orEmpty()
    } catch (e: IOException) {
        e.message.orEmpty()
    }
}

private data class Error(val message: String? = null)