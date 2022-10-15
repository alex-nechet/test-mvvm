package com.alex.android.git.di

import androidx.paging.ExperimentalPagingApi
import com.example.data.di.Koin.dataModule
import com.example.data.di.Koin.databaseModule
import com.example.domain.di.Koin.domainModule
import com.example.list.ListViewModel
import com.example.network.di.Koin.networkModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Koin {
    @ExperimentalPagingApi
    val presentationModule = module {
        viewModel { ListViewModel(get()) }
        viewModel { (movieId: Long) -> com.example.details.DetailViewModel(get(), movieId) }
    }

    @ExperimentalPagingApi
    val modules = arrayListOf(
        networkModule,
        dataModule,
        domainModule,
        presentationModule,
        databaseModule
    )


}