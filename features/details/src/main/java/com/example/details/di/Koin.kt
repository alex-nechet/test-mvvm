package com.example.details.di

import com.example.details.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsPresentationModule =
    module {
        viewModel { (movieId: Long) -> DetailViewModel(get(), movieId) }
    }

