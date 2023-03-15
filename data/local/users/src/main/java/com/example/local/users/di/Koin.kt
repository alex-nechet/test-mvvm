package com.example.local.users.di

import androidx.room.Room
import com.example.local.users.UserLocalDataSource
import com.example.local.users.UserLocalDataSourceImpl
import com.example.local.users.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val userLocalDataSource = module {
    single<UserLocalDataSource> { UserLocalDataSourceImpl(get()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "users.db"
        ).build()
    }
}
