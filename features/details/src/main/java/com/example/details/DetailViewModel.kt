package com.example.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.android.git.interactor.model.State
import com.example.domain.UserDetailsInteractor
import com.example.domain.model.BriefInfo
import com.example.domain.model.OtherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class DetailViewModel(
    private val interactor: UserDetailsInteractor,
    private val userId: Long
) : ViewModel() {

    private val _headerData = MutableStateFlow<BriefInfo?>(null)
    val headerData = _headerData.asStateFlow()

    private val _footerData = MutableStateFlow<State<List<Pair<Int, String>>>>(State.Loading())
    val footerData = _footerData.asStateFlow()

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchHeaderDetails().collectLatest { _headerData.value = it }
        }
        viewModelScope.launch(Dispatchers.IO) {
            fetchAdvancedDetails().collectLatest { _footerData.value = it }
        }
    }

    private fun fetchHeaderDetails() = interactor.getBriefUserDetails(userId)

    private fun fetchAdvancedDetails() =
        interactor.getAdvancedUserDetails(userId).map { footerState ->
            return@map when (footerState) {
                is State.Success -> State.Success(footerState.data?.toList())
                is State.Error -> State.Error(footerState.msg)
                is State.Loading -> State.Loading()
            }
        }

    private fun OtherInfo.toList() = listOf(
        R.string.company to this.company,
        R.string.location to this.location,
        R.string.email to this.email,
        R.string.about_me to this.bio,
        R.string.twitter to this.twitterUsername,
        R.string.followers to this.followers,
        R.string.following to this.following
    ).filter { it.second.isNotEmpty() }
}