package com.example.details

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.android.git.interactor.model.State
import com.example.domain.UserDetailsInteractor
import com.example.domain.model.BriefInfo
import com.example.domain.model.OtherInfo
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

    private val _footerData = MutableStateFlow<State<List<Data>>>(State.Loading())
    val footerData = _footerData.asStateFlow()

    fun fetchData() {
        viewModelScope.launch {
            _headerData.value = fetchHeaderDetails()
            fetchAdvancedDetails().collectLatest { item ->
                _footerData.value = item
            }
        }
    }

    private suspend fun fetchHeaderDetails() = interactor.getBriefUserDetails(userId)

    private fun fetchAdvancedDetails() =
        interactor.getAdvancedUserDetails(userId).map { footerState ->
            return@map when (footerState) {
                is State.Success -> State.Success(footerState.data?.toData())
                is State.Error -> State.Error(footerState.msg)
                is State.Loading -> State.Loading()
            }
        }

    private fun OtherInfo.toData() = listOf(
        Data(R.string.company, this.company),
        Data(R.string.location, this.location),
        Data(R.string.email, this.email),
        Data(R.string.about_me, this.bio),
        Data(R.string.twitter, this.twitterUsername),
        Data(R.string.followers, this.followers),
        Data(R.string.following, this.following)
    ).filter { it.text.isNotEmpty() }
}

data class Data(@StringRes val fieldRes: Int, val text: String)