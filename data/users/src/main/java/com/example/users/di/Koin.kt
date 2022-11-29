package com.example.users.di

import androidx.room.Room
import com.example.users.datasource.local.UserLocalDataSource
import com.example.users.datasource.local.UserLocalDataSourceImpl
import com.example.users.db.AppDatabase
import com.example.users.repository.UsersRepositoryImpl
import com.example.domain.di.IO
import com.example.domain.repository.UserRepository
import com.example.network.di.Koin.userRemoteDataSource
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

private val userLocalDataSource = module {
    single<UserLocalDataSource> { UserLocalDataSourceImpl(get()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "users.db"
        ).build()
    }
}

private val userRepositoryModule = module {
    single<CoroutineContext>(named(IO)) { Dispatchers.IO }
    single<UserRepository> { UsersRepositoryImpl(get(), get(), get(named(IO))) }
}

val userDataModule = userRepositoryModule + userRemoteDataSource + userLocalDataSource

