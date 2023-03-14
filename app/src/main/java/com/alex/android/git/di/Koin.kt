package com.alex.android.git.di

import com.alex.android.git.BuildConfig
import com.example.details.di.detailsPresentationModule
import com.example.domain.di.domainModule
import com.example.list.di.listPresentationModule
import com.example.local.users.di.userLocalDataSource
import com.example.network.di.Koin.networkModule
import com.example.remote.users.di.userRemoteDataSource
import com.example.users.di.userRepositoryModule

object Koin {

    private val usersListModule = listOf(
        listPresentationModule,
        userLocalDataSource,
        userRemoteDataSource,
        userRepositoryModule
    )

    private val presentationModule = detailsPresentationModule
    val modules =
        usersListModule +
                presentationModule +
                domainModule +
                networkModule(BuildConfig.DEBUG)
}