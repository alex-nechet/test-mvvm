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
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val IO = "io"

object Koin {
    private val presentationModule = module {
        viewModel { (movieId: Long) ->
            DetailViewModel(
                getUserDetailsUseCase = get(),
                userId = movieId
            )
        }
        viewModel { ListViewModel(getAllUsersUseCase = get()) }
    }

    private val domainModule = module {
        factory { GetAllUsersUseCase(get()) }
        factory { GetUserDetailsUseCase(get()) }
    }

    private val repositoriesModule = module {
        single<UserRepository> { UsersRepositoryImpl(remote = get(), local = get()) }
    }

    private val remoteModule = module {
        single<UserRemoteDataSource> {
            UserRemoteDataSourceImpl(
                api = get(),
                resultConverter = get()
            )
        }
    }

    private val localModule = userLocalDataSource

    val modules = listOf(
        presentationModule,
        domainModule,
        repositoriesModule,
        remoteModule,
        localModule,
        networkModule(BuildConfig.DEBUG)
    )
}
