package com.example.movieschallenge.services.best_movies

import com.example.movieschallenge.models.RatedMoviesModel
import com.example.movieschallenge.models.Result

class BestMoviesImp(private val service: BestMoviesServices) : BestMoviesContract{
    override suspend fun invoke(page:Int): Result<RatedMoviesModel> {
        val response = service.getMostPopularMovies(page)
        val body = response.body()

        return if (response.isSuccessful && body!=null) {
            Result.Success(value = body)
        } else {
            Result.Failure("No se pudo consultar las reseñas")
        }
    }
}