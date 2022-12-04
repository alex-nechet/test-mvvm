package com.example.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.domain.AllUsersUseCase
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

@ExperimentalPagingApi
class ListViewModel(private val interactor: AllUsersUseCase) : ViewModel() {

    val data = viewModelScope.async(start = CoroutineStart.LAZY) {
       fetchUsers().distinctUntilChanged().stateIn(viewModelScope)
    }

     private fun fetchUsers() = interactor.invoke().cachedIn(viewModelScope)

}