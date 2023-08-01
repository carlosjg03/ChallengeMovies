package com.example.movieschallenge.services.rated

import com.example.movieschallenge.models.RatedMoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RatedServices {
    @GET("3/account/1/rated/movies?language=es")
    suspend fun getRatedMovies(
        @Query("page") page: Int,
        ): Response<RatedMoviesModel>
}