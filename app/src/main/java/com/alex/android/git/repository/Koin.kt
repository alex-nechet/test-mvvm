package com.alex.android.git.repository

import androidx.room.Room
import com.alex.android.git.repository.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object Koin {

    val repositoryModule = module {
        single { UsersRepository(get(), get()) }
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