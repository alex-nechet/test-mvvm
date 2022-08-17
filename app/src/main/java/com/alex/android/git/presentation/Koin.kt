package com.alex.android.git.presentation

import androidx.paging.ExperimentalPagingApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Koin {
    @ExperimentalPagingApi
    val presentationModule = module {
        viewModel { ListViewModel(get()) }
        viewModel{ (movieId: Long) -> DetailViewModel(get(), movieId)}
    }
}