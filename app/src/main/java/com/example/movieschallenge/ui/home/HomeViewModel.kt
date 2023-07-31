package com.example.movieschallenge.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieschallenge.models.RatedMoviesModel
import com.example.movieschallenge.models.Result
import com.example.movieschallenge.services.rated.RatedContract
import kotlinx.coroutines.launch

class HomeViewModel(
    private val ratedContract: RatedContract,
) : ViewModel() {

    private val _mostrarErrorConsultar = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarErrorConsultar: LiveData<Boolean> = _mostrarErrorConsultar
    private val _mostrarListaVacia = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarListaVacia: LiveData<Boolean> = _mostrarListaVacia
    private val _ratedMovies = MutableLiveData<List<RatedMoviesModel>>().apply {
        value = listOf()
    }
    val ratedMovies: LiveData<List<RatedMoviesModel>> = _ratedMovies

    fun getRatedMovies(){
        viewModelScope.launch {
            when (val result: Result<RatedMoviesModel> = ratedContract.invoke()){
                is Result.Success -> {
                    //_ratedMovies.postValue(result.value.results)
                    _mostrarListaVacia.postValue(result.value.results.isEmpty())
                }
                is Result.Failure -> _mostrarErrorConsultar.postValue(true)
            }
        }
    }
}