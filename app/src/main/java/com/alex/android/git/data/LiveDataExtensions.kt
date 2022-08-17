package com.alex.android.git.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <A, B> combine(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A, B>> {
    return MediatorLiveData<Pair<A, B>>().apply {
        fun combine() {
            val aValue = a.value
            val bValue = b.value
            if (aValue != null && bValue != null) {
                postValue(Pair(aValue, bValue))
            }
        }
        addSource(a) { combine() }
        addSource(b) { combine() }
        combine()
    }
}

fun <A, B> LiveData<A>.combineWith(other: LiveData<B>): LiveData<Pair<A, B>> = combine(this, other)

fun <A> merge(vararg sources: LiveData<A>): LiveData<A> {
    val mergedLiveData = MediatorLiveData<A>()
    sources.forEach { item -> mergedLiveData.addSource(item) { mergedLiveData.postValue(it) } }
    return mergedLiveData
}

fun <T, K> LiveData<T>.mapNotNull(mapFun: (T) -> K?): LiveData<K> {
    val mediatorLiveData = MediatorLiveData<K>()
    mediatorLiveData.addSource(this) {
        mapFun(it)?.let { nonNullValue -> mediatorLiveData.value = nonNullValue }
    }
    return mediatorLiveData
}

fun <T> LiveData<T>.debounce(durationMillis: Long = 400L, coroutineScope: CoroutineScope) =
    MediatorLiveData<T>().also { data ->

        val source = this
        var job: Job? = null

        data.addSource(source) {
            job?.cancel()
            job = coroutineScope.launch {
                delay(durationMillis)
                data.value = source.value
            }
        }
    }