package com.example.movieschallenge.services.rated

import com.example.movieschallenge.models.LocationModel
import com.example.movieschallenge.models.RatedMoviesModel
import com.example.movieschallenge.models.Result
import com.example.movieschallenge.save.SaveDataContract
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import retrofit2.Response

class RatedImp(
    private val saveDataContract: SaveDataContract,
    private val service: RatedServices
) :RatedContract{
    override suspend fun invoke(page: Int): Result<RatedMoviesModel> {
        return getItems({ service.getRatedMovies(page) }, page, "RatedMovies.json")
    }

    private suspend fun getItems(service: suspend ()->Response<RatedMoviesModel>, page: Int, nameStorage:String) : Result<RatedMoviesModel>{
        val savedMovies = getSavedItems(nameStorage)
        try {
            val response = service.invoke()
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