package com.example.network.mappers

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class ResultConverter(private val retrofit: Retrofit) {

    suspend operator fun <T> invoke(
        request: suspend () -> Response<T>
    ): Result<T?> {
        val result = request.invoke()
        return when {
            result.isSuccessful -> Result.success(result.body())
            else -> {
                val errorMessage = parseError(result)
                Result.failure(Throwable("Code ${result.code()}\n$errorMessage"))
            }
        }
    }

    private fun parseError(response: Response<*>): String {
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
}

private data class Error(val message: String? = null)