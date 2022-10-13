package com.alex.android.git

import androidx.paging.ExperimentalPagingApi
import com.alex.android.git.presentation.Koin.presentationModule
import com.example.data.di.Koin.databaseModule
import com.example.data.di.Koin.dataModule
import com.example.domain.di.Koin.domainModule
import com.example.network.di.Koin.networkModule


object Koin {
    @ExperimentalPagingApi
    val modules = arrayListOf(
        networkModule,
        dataModule,
        domainModule,
        presentationModule,
        databaseModule
    )
}