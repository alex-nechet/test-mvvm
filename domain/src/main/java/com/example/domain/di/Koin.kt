package com.example.domain.di

import com.example.domain.AllUsersInteractor
import com.example.domain.AllUsersInteractorImpl
import com.example.domain.UserDetailsInteractorImpl
import com.example.domain.UserDetailsInteractor
import org.koin.dsl.module

object Koin {

    val domainModule = module {
        single<AllUsersInteractor> { AllUsersInteractorImpl(get()) }
        single<UserDetailsInteractor> { UserDetailsInteractorImpl(get()) }
    }
}