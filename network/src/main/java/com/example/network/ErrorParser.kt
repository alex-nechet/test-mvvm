package com.example.network

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class ErrorParser(private val retrofit: Retrofit) {
    fun parseError(response : Response<*>): Error? {
        val converter = retrofit.responseBodyConverter<Error>(
            Error::class.java,
            arrayOfNulls(0)
        )

        return try {
            converter.convert(response.errorBody() ?: return Error())
        } catch (e: IOException) {
            Error()
        }
    }
}

data class Error(val message: String? = null)