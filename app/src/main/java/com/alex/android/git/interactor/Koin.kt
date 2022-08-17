package com.alex.android.git.interactor

import org.koin.dsl.module

object Koin {

    val interactorsModule = module {
        single<UsersInteractor> { UsersInteractorImpl(get()) }
    }
}