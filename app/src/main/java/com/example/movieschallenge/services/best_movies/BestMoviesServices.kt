package com.example.movieschallenge.services.best_movies

import com.example.movieschallenge.models.RatedMoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BestMoviesServices {

    @GET("3/movie/popular?language=es")
    suspend fun getMostPopularMovies(
        @Query("page") page: Int,
    ): Response<RatedMoviesModel>
    @GET("3/movie/top_rated?language=es")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): Response<RatedMoviesModel>
    @GET("3/movie/8/recommendations?language=es")
    suspend fun getRecommendationsMovies(
        @Query("page") page: Int,
    ): Response<RatedMoviesModel>
}