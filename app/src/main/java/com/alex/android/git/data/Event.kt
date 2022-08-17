package com.alex.android.git.data

class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    fun getContentIfNotHandled(): T? {
        return when {
            hasBeenHandled -> null
            else -> {
                hasBeenHandled = true
                content
            }
        }
    }

    fun peekContent(): T = content
}