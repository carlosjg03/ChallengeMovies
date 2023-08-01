package com.example.movieschallenge.services.best_movies

import com.example.movieschallenge.models.RatedMoviesModel
import com.example.movieschallenge.models.Result

interface BestMoviesContract {
    suspend fun getMostPopularMovies(page:Int): Result<RatedMoviesModel>
    suspend fun getTopRatedMovies(page:Int): Result<RatedMoviesModel>
    suspend fun getRecommendationsMovies(page:Int): Result<RatedMoviesModel>
}