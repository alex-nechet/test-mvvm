package com.example.network

import retrofit2.Response

class ResponseConverter(private val errorParser: ErrorParser) {
    suspend fun <T> toResult(request: suspend () -> Response<T>): Result<T?> {
        return try {
            val result = request.invoke()
            when {
                result.isSuccessful -> return Result.success(result.body())
                else -> {
                    val errorResponse = errorParser.parseError(result)
                    val code = result.code()
                    val message = errorResponse?.message ?: "Unknown Error"
                    Result.failure(
                        Throwable(message = "Response code:${code} $message")
                    )
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}


