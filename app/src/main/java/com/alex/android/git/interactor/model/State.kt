package com.alex.android.git.interactor.model

sealed interface State<out T> {
    data class Success<T>(val data: T?) : State<T>
    class Loading<T> : State<T>
    data class Error<T>(val msg: String?) : State<T>
}

