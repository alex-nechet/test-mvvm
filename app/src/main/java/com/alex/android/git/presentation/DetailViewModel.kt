package com.alex.android.git.presentation

import androidx.lifecycle.ViewModel
import com.example.domain.UserDetailsInteractor


class DetailViewModel(
    private val interactor: UserDetailsInteractor,
    private val userId: Long
) : ViewModel() {

    fun fetchHeaderDetails() = interactor.getBriefUserDetails(userId)

    fun fetchAdvancedDetails() = interactor.getAdvancedUserDetails(userId)

}