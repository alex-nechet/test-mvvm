package com.example.domain.di

import com.example.domain.GetAllUsersUseCase
import com.example.domain.GetUserDetailsUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val IO = "io"

val domainModule = module {
    factory { GetAllUsersUseCase(get()) }
    factory  { GetUserDetailsUseCase(get(), get(named(IO))) }
}
