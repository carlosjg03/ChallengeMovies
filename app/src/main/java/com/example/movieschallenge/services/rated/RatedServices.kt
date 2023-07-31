package com.example.movieschallenge.services.rated

import com.example.movieschallenge.models.RatedMoviesModel
import retrofit2.Response
import retrofit2.http.GET

interface RatedServices {
    @GET("1/rated/movies?language=es")
    suspend fun getRatedMovies(): Response<RatedMoviesModel>
}