package com.alex.android.git.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alex.android.git.data.mapNotNull
import com.alex.android.git.data.model.UserDb
import com.alex.android.git.interactor.UsersInteractor
import com.example.network.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class ListViewModel(private val repository: UsersInteractor) : ViewModel() {

    private val _data = MutableLiveData<Result<PagingData<UserDb>>>()
    val data = _data.mapNotNull { if (it is Result.Success) it else null }.mapNotNull { it.data }

    val loading = _data.map { it is Result.Loading }
    val error = _data.map { it is Result.Error }
    val errorMessage = _data.map { if (it is Result.Error) it else null }
        .mapNotNull { it }
        .map { it.msg }


    fun fetchUsers() = viewModelScope.launch {
        repository.getUsers()
            .onStart { _data.postValue(Result.Loading()) }
            .catch { _data.postValue(Result.Error(it.message)) }
            .cachedIn(viewModelScope)
            .map { Result.Success(it) }
            .collectLatest {
                _data.postValue(it)
            }
    }

}