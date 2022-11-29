package com.example.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.details.mappers.toBriefInfo
import com.example.details.mappers.toData
import com.example.details.model.UserDetails
import com.example.domain.UserDetailsInteractor
import com.example.domain.common.model.State
import com.example.domain.common.model.map
import kotlinx.coroutines.flow.*
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
            }
                .distinctUntilChanged()
                .collectLatest { _userDetails.value = it }
        }
    }

    private fun getUserDetails(userId: Long) = interactor.getUserDetails(userId)
}



