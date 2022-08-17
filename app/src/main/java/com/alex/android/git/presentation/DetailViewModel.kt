package com.alex.android.git.presentation

import androidx.lifecycle.*
import com.alex.android.git.data.Event
import com.alex.android.git.data.combineWith
import com.alex.android.git.data.converters.toBriefInfo
import com.alex.android.git.data.converters.toOtherInfo
import com.alex.android.git.data.mapNotNull
import com.alex.android.git.data.model.BriefInfo
import com.alex.android.git.data.model.OtherInfo
import com.alex.android.git.data.model.User
import com.alex.android.git.data.model.UserDb
import com.alex.android.git.network.Result
import com.alex.android.git.interactor.UsersInteractor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: UsersInteractor,
    private val userId: Long
) : ViewModel() {

    private val _headerInfo = MutableLiveData<Result<BriefInfo>>()
    val headerInfo: LiveData<BriefInfo> = _headerInfo.mapNotNull { it.data }

    private val _bodyInfo = MutableLiveData<Result<OtherInfo>>()
    val bodyInfo: LiveData<OtherInfo> = _bodyInfo.mapNotNull { it.data }

    val loading = _bodyInfo.map { it is Result.Loading }

    val error = _bodyInfo.combineWith(_headerInfo)
        .map { it.first is Result.Error || it.second is Result.Error }

    val errorMessage = _headerInfo.combineWith(_bodyInfo).map {
        when {
            it.first is Result.Error -> it.first as Result.Error
            it.second is Result.Error -> it.second as Result.Error
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