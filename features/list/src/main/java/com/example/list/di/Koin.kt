package com.example.list.di

import androidx.paging.ExperimentalPagingApi
import com.example.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


@OptIn(ExperimentalPagingApi::class)
val listPresentationModule = module {
    viewModel { ListViewModel(get()) }
}
