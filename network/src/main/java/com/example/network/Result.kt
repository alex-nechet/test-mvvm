package com.example.network

sealed class Result<out T>(open val data: T? = null) {
    class Success<out T>(override val data: T?) : Result<T>(data)
    class Loading<out T> : Result<T>(null)
    class Error<out T>(val msg: String?, val cause: ResponseConverter.Error? = null) : Result<T>(null)
}

