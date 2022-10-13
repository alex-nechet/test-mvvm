package com.alex.android.git.interactor.di

import com.alex.android.git.interactor.AllUsersInteractor
import com.alex.android.git.interactor.AllUsersInteractorImpl
import com.alex.android.git.interactor.UserDetailsInteractorImpl
import com.alex.android.git.interactor.UserDetailsIteractor
import org.koin.dsl.module

object Koin {

    val interactorsModule = module {
        single<AllUsersInteractor> { AllUsersInteractorImpl(get()) }
        single<UserDetailsIteractor> { UserDetailsInteractorImpl(get()) }
    }
}