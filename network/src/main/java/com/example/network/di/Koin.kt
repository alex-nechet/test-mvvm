package com.example.network.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.example.network.ApiProvider
import com.example.network.BuildConfig
import com.example.network.ResponseConverter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Koin {
    val networkModule = module {
        /**
         * Use tokenless calls as it is or add your TOKEN to gradle
         */
        single {
//            val authInterceptor = Interceptor { chain ->
//                val requestBuilder = chain.request().newBuilder()
//                requestBuilder.addHeader("Authorization", "token ${BuildConfig.TOKEN}")
//                chain.proceed(requestBuilder.build())
//            }

            val loggingInterceptor = HttpLoggingInterceptor()
                .apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

            OkHttpClient.Builder()
//                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        }

        single {
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        single { ResponseConverter(get()) }
        factory {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(get()))
                .client(get())
                .build()
        }
        single { ApiProvider(get(), get()) }
    }
}