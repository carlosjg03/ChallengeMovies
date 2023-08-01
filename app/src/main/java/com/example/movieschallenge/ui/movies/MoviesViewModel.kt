package com.example.movieschallenge.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieschallenge.models.RatedMovieModel
import com.example.movieschallenge.models.RatedMoviesModel
import com.example.movieschallenge.models.Result
import com.example.movieschallenge.services.best_movies.BestMoviesContract
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val bestMoviesContract: BestMoviesContract
) : ViewModel() {
    private var pageMostPopularMovies = 0
    private var pageTopRatedMovies = 0
    private var pageRecommendationsMovies = 0

    private val _mostrarErrorConsultarTopRated = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarErrorConsultarTopRated: LiveData<Boolean> = _mostrarErrorConsultarTopRated
    private val _mostrarListaVaciaTopRated = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarListaVaciaTopRated: LiveData<Boolean> = _mostrarListaVaciaTopRated


    private val _topRatedMovies = MutableLiveData<List<RatedMovieModel>>().apply {
        value = listOf()
    }
    val topRatedMovies: LiveData<List<RatedMovieModel>> = _topRatedMovies
    private val _mostrarErrorConsultarMostPopular = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarErrorConsultarMostPopular: LiveData<Boolean> = _mostrarErrorConsultarMostPopular
    private val _mostrarListaVaciaMostPopular = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarListaVaciaMostPopular: LiveData<Boolean> = _mostrarListaVaciaMostPopular
    private val _mostPopularMovies = MutableLiveData<List<RatedMovieModel>>().apply {
        value = listOf()
    }
    val mostPopularMovies: LiveData<List<RatedMovieModel>> = _mostPopularMovies

    private val _recommendationsMovies = MutableLiveData<List<RatedMovieModel>>().apply {
        value = listOf()
    }
    val recommendationsMovies: LiveData<List<RatedMovieModel>> = _recommendationsMovies
    private val _mostrarErrorRecommendations = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarErrorRecommendations: LiveData<Boolean> = _mostrarErrorRecommendations
    private val _mostrarListaVaciaRecommendations = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarListaVaciaRecommendations: LiveData<Boolean> = _mostrarListaVaciaRecommendations


    fun getMovies(){
        viewModelScope.launch {
            launch {
                loadMoreMostPopularMovies()
            }
            launch {
                loadMoreRatedMovies()
            }
            launch {
                loadMoreRecommendationMovies()
            }
        }
    }


    fun loadMoreMostPopularMovies(){
        viewModelScope.launch {
            pageMostPopularMovies = getItems(
                bestMoviesContract.getMostPopularMovies(pageMostPopularMovies+1),
                pageMostPopularMovies,
                _mostPopularMovies,
                _mostrarListaVaciaMostPopular,
                _mostrarErrorConsultarMostPopular
            )
        }
    }
    fun loadMoreRatedMovies(){
        viewModelScope.launch {
            pageTopRatedMovies = getItems(
                bestMoviesContract.getTopRatedMovies(pageTopRatedMovies+1),
                pageTopRatedMovies,
                _topRatedMovies,
                _mostrarListaVaciaTopRated,
                _mostrarErrorConsultarTopRated
            )
        }
    }
    fun loadMoreRecommendationMovies(){
        viewModelScope.launch {
            pageRecommendationsMovies = getItems(
                bestMoviesContract.getRecommendationsMovies(pageRecommendationsMovies+1),
                pageRecommendationsMovies,
                _recommendationsMovies,
                _mostrarListaVaciaRecommendations,
                _mostrarErrorRecommendations
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