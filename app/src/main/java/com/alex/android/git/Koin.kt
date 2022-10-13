package com.alex.android.git

import androidx.paging.ExperimentalPagingApi
import com.alex.android.git.interactor.di.Koin.interactorsModule
import com.alex.android.git.presentation.Koin.presentationModule
import com.example.data.di.Koin.databaseModule
import com.example.data.di.Koin.repositoryModule
import com.example.network.di.Koin.networkModule


object Koin {
    @ExperimentalPagingApi
    val modules = arrayListOf(
        networkModule,
        repositoryModule,
        interactorsModule,
        presentationModule,
        databaseModule
    )
}