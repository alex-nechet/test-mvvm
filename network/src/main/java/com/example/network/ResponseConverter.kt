package com.example.network

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class ResponseConverter(private val retrofit: Retrofit) {
     suspend fun <T> toResult(request: suspend () -> Response<T>): Result<T> {
        return try {
            val result = request.invoke()
            when {
                result.isSuccessful -> return Result.Success(result.body())
                else -> {
                    val errorResponse = result.parseError(retrofit)
                    Result.Error(
                        msg = errorResponse?.status_message ?: "Unknown Error",
                        cause = errorResponse
                    )
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Error(e.message)
        }
    }

    data class Error(val status_code: Int = 0, val status_message: String? = null)

}

fun Response<*>.parseError(retrofit: Retrofit): ResponseConverter.Error? {
    val converter = retrofit.responseBodyConverter<ResponseConverter.Error>(ResponseConverter.Error::class.java, arrayOfNulls(0))
    return try {
        converter.convert(this.errorBody() ?: return ResponseConverter.Error())
    } catch (e: IOException) {
        ResponseConverter.Error()
    }
}
