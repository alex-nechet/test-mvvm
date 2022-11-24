package com.example.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.AllUsersInteractor
import com.example.domain.model.BriefInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class ListViewModel(private val interactor: AllUsersInteractor) : ViewModel() {

    private val _data = MutableStateFlow<PagingData<BriefInfo>>(PagingData.empty())
    val data = _data.asStateFlow()

    private fun fetchUsers() = interactor.invoke().cachedIn(viewModelScope)

    fun fetchData() {
        viewModelScope.launch {
            fetchUsers().collectLatest { _data.value = it }
        }
    }
}