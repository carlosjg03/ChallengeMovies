package com.example.movieschallenge.ui.rated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieschallenge.models.RatedMovieModel
import com.example.movieschallenge.models.RatedMoviesModel
import com.example.movieschallenge.models.Result
import com.example.movieschallenge.services.rated.RatedContract
import kotlinx.coroutines.launch

class RatedViewModel(
    private val ratedContract: RatedContract,
) : ViewModel() {
    private var pageratedMovies = 0

    private val _mostrarErrorConsultar = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarErrorConsultar: LiveData<Boolean> = _mostrarErrorConsultar
    private val _mostrarListaVacia = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarListaVacia: LiveData<Boolean> = _mostrarListaVacia
    private val _ratedMovies = MutableLiveData<List<RatedMovieModel>>().apply {
        value = listOf()
    }
    val ratedMovies: LiveData<List<RatedMovieModel>> = _ratedMovies

    fun loadMoreRecommendationMovies(){
        viewModelScope.launch {
            pageratedMovies = getItems(
                ratedContract(pageratedMovies+1),
                pageratedMovies,
                _ratedMovies,
                _mostrarListaVacia,
                _mostrarErrorConsultar
            )
        }
    }
    private fun getItems(
        service: Result<RatedMoviesModel>,
        page:Int,
        listUpdate:MutableLiveData<List<RatedMovieModel>>,
        isEmpty:MutableLiveData<Boolean>,
        error:MutableLiveData<Boolean>,
    ):Int{
        service.let {result->
            when (result){
                is Result.Success -> {
                    listUpdate.postValue(result.value.results)
                    isEmpty.postValue(result.value.results.isEmpty())
                    return result.value.page
                }
                is Result.Failure -> error.postValue(true)
            }
        }
        return page
    }
}