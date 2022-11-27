package com.example.domain.di

import com.example.domain.AllUsersInteractor
import com.example.domain.UserDetailsInteractorImpl
import com.example.domain.UserDetailsInteractor
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val IO = "io"

val domainModule = module {
    single { AllUsersInteractor(get()) }
    single<UserDetailsInteractor> { UserDetailsInteractorImpl(get(), get(named(IO))) }
}
