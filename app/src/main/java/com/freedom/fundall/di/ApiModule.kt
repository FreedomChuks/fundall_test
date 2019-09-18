package com.freedom.fundall.di

import com.freedom.fundall.api.ApiServices
import com.freedom.fundall.utils.Constants.Companion.BASEURL
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val LOGGING_INTERCEPTOR="LOGGING_INTERCEPTOR"

val apiModule= module {

    single { Gson() }

    val httpInterceptor = HttpLoggingInterceptor()
    httpInterceptor.apply {
        httpInterceptor.level = HttpLoggingInterceptor.Level.BODY



        single {
            OkHttpClient.Builder()
                .addInterceptor(httpInterceptor)
                .build()

        }

        single {
            Retrofit.Builder()
                .client(get())
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create(get()))
                .build()
                .create(ApiServices::class.java)
        }
    }
}