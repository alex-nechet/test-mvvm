package com.alex.android.git.di

import com.alex.android.git.BuildConfig
import com.example.details.DetailViewModel
import com.example.domain.GetAllUsersUseCase
import com.example.domain.GetUserDetailsUseCase
import com.example.domain.repository.UserRepository
import com.example.list.ListViewModel
import com.example.local.users.di.userLocalDataSource
import com.example.network.di.Koin.networkModule
import com.example.remote.users.UserRemoteDataSource
import com.example.remote.users.UserRemoteDataSourceImpl
import com.example.users.repository.UsersRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

private const val IO = "io"


object Koin {
    private val coroutinesContextModule = module {
        single<CoroutineContext>(named(IO)) { Dispatchers.IO }
    }


    private val presentationModule = module {
        viewModel { (movieId: Long) -> DetailViewModel(get(), movieId) }
        viewModel { ListViewModel(get()) }
    }

    private val domainModule = module {
        factory { GetAllUsersUseCase(get()) }
        factory { GetUserDetailsUseCase(get(), get(named(IO))) }
    }

    private val repositoriesModule = module {
        single<UserRepository> { UsersRepositoryImpl(get(),get(), get(named(IO))) }
    }

    private val remoteModule = module {
        single<UserRemoteDataSource> { UserRemoteDataSourceImpl(get(), get()) }
    }

    private val localModule = userLocalDataSource

    val modules = listOf(
        coroutinesContextModule,
        presentationModule,
        domainModule,
        repositoriesModule,
        remoteModule,
        localModule,
        networkModule(BuildConfig.DEBUG)
    )
}