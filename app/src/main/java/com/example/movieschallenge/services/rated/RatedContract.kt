package com.example.movieschallenge.services.rated

import com.example.movieschallenge.models.RatedMoviesModel
import com.example.movieschallenge.models.Result

interface RatedContract {
    suspend operator fun invoke(page: Int): Result<RatedMoviesModel>
}