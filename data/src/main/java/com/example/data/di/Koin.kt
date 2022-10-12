package com.example.data.di

import androidx.room.Room
import com.example.data.db.AppDatabase
import com.example.data.repository.UserRepository
import com.example.data.repository.UsersRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object Koin {

    val repositoryModule = module {
        single<UserRepository> { UsersRepositoryImpl(get(), get()) }
    }

    val databaseModule = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java, "users.db"
            ).build()
        }
    }
}