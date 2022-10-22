package com.example.details

import androidx.lifecycle.ViewModel
import com.alex.android.git.interactor.model.State
import com.example.domain.UserDetailsInteractor
import com.example.domain.model.OtherInfo
import kotlinx.coroutines.flow.map


class DetailViewModel(
    private val interactor: UserDetailsInteractor,
    private val userId: Long
) : ViewModel() {

    fun fetchHeaderDetails() = interactor.getBriefUserDetails(userId)

    fun fetchAdvancedDetails() = interactor.getAdvancedUserDetails(userId).map {
        when (it) {
            is State.Success -> State.Success(it.data?.toList())

            is State.Error -> State.Error(it.msg)
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