package com.example.movieschallenge.services

import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object API {
    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS))
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    inline fun <reified T> getServices(): T {
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/account/").build().create(T::class.java)
    }

}