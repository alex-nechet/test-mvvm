package com.example.details

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.details.mappers.toBriefInfo
import com.example.details.mappers.toData
import com.example.details.model.UserDetails
import com.example.domain.UserDetailsInteractor
import com.example.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class DetailViewModel(
    private val interactor: UserDetailsInteractor,
    private val userId: Long
) : ViewModel() {

    private val _userDetails = MutableStateFlow<State<UserDetails>>(State.Loading())
    val userDetails = _userDetails.asStateFlow()

    fun fetchData() {
        viewModelScope.launch {
            getUserDetails(userId).map { state ->
                state.map { UserDetails(it.toBriefInfo(), it.toData()) }
            }.collectLatest { _userDetails.value = it }
        }
    }

    private fun getUserDetails(userId: Long) = interactor.getUserDetails(userId)
}



