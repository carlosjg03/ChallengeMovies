package com.example.movieschallenge.services

import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object API {

    private val logging by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(userAgent)
            .addInterceptor(logging)
            .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS))
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val userAgent = Interceptor {
        val request = it.request()
        val newRequest = request.newBuilder()
            .header("User-Agent", "MoviesChallenge")
            .header("Authorization","Bearer "+ "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzNWU0YzlmNjdiMTU3YzE4OWViNDFjYjA3MmQ2YmQ0NSIsInN1YiI6IjY0YzZkMThkOTVjZTI0MDEwMTJmYTczYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.k-1BjDw34IhkDnuv0Y_0YTjE1lvq36ehLWaN_0HDe0U")
            .build()

        val response = it.proceed(newRequest)
        response
    }

    inline fun <reified T> getServices(): T {
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/").build().create(T::class.java)
    }

}