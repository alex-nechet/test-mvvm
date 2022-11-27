package com.example.network.di

import com.example.network.ErrorParser
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.example.network.GitApi
import com.example.network.ResponseConverter
import com.example.network.remote.UserRemoteDataSource
import com.example.network.remote.UserRemoteDataSourceImpl
import com.squareup.moshi.JsonAdapter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val LIB_SECRET_NAME = "secret"
private const val HEADER_AUTHORIZATION = "Authorization"
private const val AUTH_INTERCEPTOR = "authInterceptor"
private const val LOGGING_INTERCEPTOR = "loggingInterceptor"

object Koin {

    init {
        System.loadLibrary(LIB_SECRET_NAME);
    }

    private external fun baseUrlJNI(): String

    private external fun tokenJNI(): String

    val userRemoteDataSource = module {
        factory<UserRemoteDataSource> { UserRemoteDataSourceImpl(get(), get()) }
    }

    fun networkModule(debuggable: Boolean) = module {
        /**
         * Use tokenless calls as it is or add your TOKEN to secret.cpp
         */
        factory(named(AUTH_INTERCEPTOR)) {
            Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.addHeader(HEADER_AUTHORIZATION, "token ${tokenJNI()}")
                chain.proceed(requestBuilder.build())
            }
        }
        factory<Interceptor>(named(LOGGING_INTERCEPTOR)) {
            HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        }
        factory {
            val builder = OkHttpClient.Builder()
            if (debuggable) {
                builder.addInterceptor(get<Interceptor>(named(LOGGING_INTERCEPTOR)))
            }
            //for testing purposes token can be empty
            if (tokenJNI().isNotEmpty()) {
                builder.addInterceptor(get<Interceptor>(named(AUTH_INTERCEPTOR)))
            }
            builder.build()
        }

        factory { Moshi.Builder().add(get()).build() }
        factory<JsonAdapter.Factory> { KotlinJsonAdapterFactory() }
        factory<Converter.Factory> { MoshiConverterFactory.create(get()) }
        single { ErrorParser(get()) }
        single { ResponseConverter(get()) }
        factory { get<Retrofit>().create(GitApi::class.java) }

        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl(baseUrlJNI())
                .addConverterFactory(get())
                .client(get())
                .build()
        }
    }
}