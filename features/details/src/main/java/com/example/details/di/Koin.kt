package com.example.details.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsPresentationModule =
    module {
        viewModel { (movieId: Long) -> com.example.details.DetailViewModel(get(), movieId) }
    }

