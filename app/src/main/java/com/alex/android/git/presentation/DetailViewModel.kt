package com.alex.android.git.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.alex.android.git.data.combineWith
import com.alex.android.git.data.mapNotNull
import com.alex.android.git.interactor.State
import com.alex.android.git.interactor.model.OtherInfo
import com.alex.android.git.interactor.UsersInteractor
import com.alex.android.git.interactor.model.BriefInfo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: UsersInteractor,
    private val userId: Long
) : ViewModel() {

    private val _headerInfo = MutableLiveData<BriefInfo>()
    val headerInfo: LiveData<BriefInfo> = _headerInfo

    private val _bodyInfo = MutableLiveData<State<OtherInfo>>()
    val bodyInfo: LiveData<OtherInfo> = _bodyInfo.map { state ->
        when (state) {
            is State.Success -> state.data
            else -> null
        }
    }
        .mapNotNull { it }

    val loading = _bodyInfo.map { it is State.Loading }

    val error = _bodyInfo.combineWith(_headerInfo)
        .map { it.first is State.Error }

    val errorMessage = _headerInfo.combineWith(_bodyInfo).map {
        when (it.second) {
            is State.Error -> it.second as State.Error
            else -> null
        }
    }
        .mapNotNull { it }
        .map { result -> result.msg }

    fun fetchHeaderDetails() {
        viewModelScope.launch {
            repository.getBriefUserDetails(userId)
                .collectLatest { _headerInfo.postValue(it) }
        }
    }

    fun fetchAdvancedDetails() {
        viewModelScope.launch {
            repository.getAdvancedUserDetails(userId).collectLatest {
                _bodyInfo.postValue(it)
            }
        }
    }
}