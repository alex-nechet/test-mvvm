package com.example.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.GetAllUsersUseCase
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.stateIn

class ListViewModel(private val getAllUsersUseCase: GetAllUsersUseCase) : ViewModel() {

    val data = viewModelScope.async(start = CoroutineStart.LAZY) {
        getAllUsersUseCase().cachedIn(viewModelScope).stateIn(viewModelScope)
    }
}
