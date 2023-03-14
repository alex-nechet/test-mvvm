package com.example.remote.users.di

import com.example.remote.users.UserRemoteDataSource
import com.example.remote.users.UserRemoteDataSourceImpl
import org.koin.dsl.module

val userRemoteDataSource = module {
    factory<UserRemoteDataSource> { UserRemoteDataSourceImpl(get(), get()) }
}