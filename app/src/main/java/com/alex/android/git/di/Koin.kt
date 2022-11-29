package com.alex.android.git.di

import com.alex.android.git.BuildConfig
import com.example.users.di.userDataModule
import com.example.details.di.detailsPresentationModule
import com.example.domain.di.domainModule
import com.example.list.di.listPresentationModule
import com.example.network.di.Koin.networkModule

object Koin {

    private val presentationModule = listPresentationModule + detailsPresentationModule
    private val _domainModule = domainModule
    private val dataModule = userDataModule
    val modules =
        presentationModule +
        _domainModule +
        dataModule +
        networkModule(BuildConfig.DEBUG)
}