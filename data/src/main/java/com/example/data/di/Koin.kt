package com.example.data.di

import androidx.room.Room
import com.example.data.db.AppDatabase
import com.example.data.repository.UsersRepositoryImpl
import com.example.domain.di.IO
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext


object Koin {

    val dataModule = module {
        single<CoroutineContext>(named(IO)) { Dispatchers.IO }
        single<UserRepository> { UsersRepositoryImpl(get(), get(), get(named(IO))) }
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