package com.alex.android.git.presentation

import androidx.lifecycle.ViewModel
import com.alex.android.git.interactor.UserDetailsIteractor


class DetailViewModel(
    private val interactor: UserDetailsIteractor,
    private val userId: Long
) : ViewModel() {

    fun fetchHeaderDetails() = interactor.getBriefUserDetails(userId)

    fun fetchAdvancedDetails() = interactor.getAdvancedUserDetails(userId)

}