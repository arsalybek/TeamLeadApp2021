package com.example.teamleadapp.di

import com.example.teamleadapp.data.remote.GithubService
import com.example.teamleadapp.data.remote.MockService
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {

    single { okHttp() }
    single { retrofit(get()) }
    single { retrofit(get()).create(GithubService::class.java)}
    single { retrofit(get()).create(MockService::class.java)}
}

private fun retrofit(client: OkHttpClient): Retrofit {
    val gson = GsonBuilder()
        .create()

    return Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
//        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()
}
private val requestBodyLoggerInterceptor: Interceptor
    get() = HttpLoggingInterceptor().apply {
        HttpLoggingInterceptor.Level.BODY
    }

private val requestHeaderLoggerInterceptor: Interceptor
    get() = HttpLoggingInterceptor().apply {
        HttpLoggingInterceptor.Level.HEADERS
    }

private fun okHttp() = OkHttpClient.Builder()
    .addInterceptor(requestBodyLoggerInterceptor)
    .addInterceptor(requestHeaderLoggerInterceptor)
    .build()
