package com.example.movieschallenge.services.rated

import com.example.movieschallenge.models.RatedMoviesModel
import com.example.movieschallenge.models.Result

class RatedImp(private val service: RatedServices) :RatedContract{
    override suspend fun invoke(): Result<RatedMoviesModel> {
        val response = service.getRatedMovies()
        val body = response.body()

        return if (response.isSuccessful && body!=null) {
            Result.Success(value = body)
        } else {
            Result.Failure("No se pudo consultar las reseñas")
        }
    }
}