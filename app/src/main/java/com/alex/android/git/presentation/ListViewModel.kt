package com.alex.android.git.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.alex.android.git.interactor.AllUsersInteractor

@ExperimentalPagingApi
class ListViewModel(private val interactor: AllUsersInteractor) : ViewModel() {

    fun fetchUsers() = interactor.getUsers().cachedIn(viewModelScope)
}