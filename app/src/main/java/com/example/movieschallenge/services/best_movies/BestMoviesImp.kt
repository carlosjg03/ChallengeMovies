package com.example.movieschallenge.services.best_movies

import com.example.movieschallenge.models.RatedMoviesModel
import com.example.movieschallenge.models.Result
import com.example.movieschallenge.save.SaveDataContract
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import retrofit2.Response

class BestMoviesImp(
    private val saveDataContract: SaveDataContract,
    private val service: BestMoviesServices) : BestMoviesContract{
    override suspend fun getMostPopularMovies(page: Int): Result<RatedMoviesModel> {
        return getItems(service.getMostPopularMovies(page),page,"MostPopularMovies.json")
    }
    override suspend fun getTopRatedMovies(page: Int): Result<RatedMoviesModel> {
        return getItems(service.getTopRatedMovies(page),page,"TopRatedMovies.json")
    }

    override suspend fun getRecommendationsMovies(page: Int): Result<RatedMoviesModel> {
        return getItems(service.getRecommendationsMovies(page),page,"RecommendationsMovies.json")
    }

    private fun getItems(service:Response<RatedMoviesModel>,page: Int, nameStorage:String) : Result<RatedMoviesModel>{
        val savedMovies = getSavedItems(nameStorage)
        try {
            val response = service
            val body = response.body()
            if (response.isSuccessful && body!=null) {
                if(page!=1) {
                    body.results.addAll(0, savedMovies.results)
                }
                saveDataContract.saveData(nameStorage, Gson().toJson(body))
                return Result.Success(value = body)
            }
        } catch (_:Exception){ }
        return if(savedMovies.page == 0){
            Result.Failure("No se pudo consultar la información")
        } else {
            Result.Success(value = savedMovies)
        }
    }

    private fun getSavedItems(nameStorage:String):RatedMoviesModel {
        val savedData = saveDataContract.getData(nameStorage)
        if (savedData.isNotBlank()) {
             return Gson().fromJson(savedData, (object : TypeToken<RatedMoviesModel>() {}).type)
        }
        return RatedMoviesModel()
    }
}