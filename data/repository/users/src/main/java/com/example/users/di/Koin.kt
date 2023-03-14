package com.example.users.di

import com.example.domain.di.IO
import com.example.domain.repository.UserRepository
import com.example.local.users.di.userLocalDataSource
import com.example.users.repository.UsersRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val userRepositoryModule = module {
    single<CoroutineContext>(named(IO)) { Dispatchers.IO }
    single<UserRepository> { UsersRepositoryImpl(get(), get(), get(named(IO))) }
}
