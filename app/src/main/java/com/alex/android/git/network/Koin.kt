package com.alex.android.git.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.alex.android.git.BuildConfig
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.math.sin

private const val API_KEY = "api_key"

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
        factory { ApiProvider(get()) }
    }
}