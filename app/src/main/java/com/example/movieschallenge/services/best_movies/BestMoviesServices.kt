package com.example.movieschallenge.services.best_movies

import com.example.movieschallenge.models.RatedMoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BestMoviesServices {
    @GET("3/movie/popular?language=es&page={page}")
    suspend fun getMostPopularMovies(
        @Path("page") page: Int,
    ): Response<RatedMoviesModel>
}