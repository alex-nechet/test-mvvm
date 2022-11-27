package com.example.domain.model

sealed interface State<out T> {
    data class Success<T>(val data: T) : State<T>
    class Loading<T> : State<T>
    data class Error<T>(val errorType: ErrorType) : State<T>
}

fun <T, R> State<T>.map(transform: (data: T) -> R) : State<R> = when(this){
    is State.Error<T> -> State.Error(this.errorType)
    is State.Loading<T> -> State.Loading()
    is State.Success<T> -> State.Success(transform.invoke(this.data))
}


