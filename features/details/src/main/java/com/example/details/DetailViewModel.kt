package com.example.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.details.mappers.toData
import com.example.domain.GetUserDetailsUseCase
import com.example.domain.common.model.map
import com.example.domain.model.UserDetails
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map


class DetailViewModel(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val userId: Long
) : ViewModel() {

    val userDetails = viewModelScope.async(start = CoroutineStart.LAZY) { fetchData() }

    private suspend fun fetchData() = getUserDetails(userId).map { state ->
        state.map { UserDetails(it, it.toData()) }
    }.distinctUntilChanged().stateIn(viewModelScope)

    private fun getUserDetails(userId: Long) = getUserDetailsUseCase(userId)
}



