package com.example.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alex.android.git.interactor.model.State
import com.alex.android.git.interactor.model.User
import com.example.domain.AllUsersInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class ListViewModel(private val interactor: AllUsersInteractor) : ViewModel() {

    private val _data = MutableStateFlow<State<PagingData<User>>>(State.Loading())
    val data = _data.asStateFlow()

    private fun fetchUsers() = interactor.getUsers().cachedIn(viewModelScope)

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchUsers().onStart { _data.value = State.Loading() }
                .catch { _data.value = State.Error(it.message) }
                .collectLatest { _data.value = State.Success(it) }
        }
    }
}