package com.example.movieschallenge.services.best_movies

import com.example.movieschallenge.models.RatedMoviesModel
import com.example.movieschallenge.models.Result

interface BestMoviesContract {
    suspend operator fun invoke(page:Int): Result<RatedMoviesModel>
}