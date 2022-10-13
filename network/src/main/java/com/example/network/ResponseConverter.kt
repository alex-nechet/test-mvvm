package com.example.network

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class ResponseConverter(private val retrofit: Retrofit) {
    suspend fun <T> toResult(request: suspend () -> Response<T>): Result<T?> {
        return try {
            val result = request.invoke()
            when {
                result.isSuccessful -> return Result.success(result.body())
                else -> {
                    val errorResponse = result.parseError(retrofit)
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

    data class Error(val message: String? = null)
}

fun Response<*>.parseError(retrofit: Retrofit): ResponseConverter.Error? {
    val converter = retrofit.responseBodyConverter<ResponseConverter.Error>(
        ResponseConverter.Error::class.java,
        arrayOfNulls(0)
    )

    return try {
        converter.convert(this.errorBody() ?: return ResponseConverter.Error())
    } catch (e: IOException) {
        ResponseConverter.Error()
    }
}
