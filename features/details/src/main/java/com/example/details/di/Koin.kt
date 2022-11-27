package com.example.details.di

import com.example.domain.di.domainModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsPresentationModule =
    module {
        viewModel { (movieId: Long) -> com.example.details.DetailViewModel(get(), movieId) }
    }

