package com.alex.android.git

import androidx.paging.ExperimentalPagingApi
import com.alex.android.git.interactor.Koin.interactorsModule
import com.alex.android.git.network.Koin.networkModule
import com.alex.android.git.presentation.Koin.presentationModule
import com.alex.android.git.repository.Koin.databaseModule
import com.alex.android.git.repository.Koin.repositoryModule


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