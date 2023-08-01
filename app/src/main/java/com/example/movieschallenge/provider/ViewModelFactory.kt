package com.example.movieschallenge.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieschallenge.services.best_movies.BestMoviesContract
import com.example.movieschallenge.services.locations.LocationsContract
import com.example.movieschallenge.services.rated.RatedContract
import com.example.movieschallenge.ui.movies.MoviesViewModel
import com.example.movieschallenge.ui.rated.RatedViewModel
import com.example.movieschallenge.ui.ubicaciones.UbicacionesViewModel

class ViewModelFactory(
    private val ratedContract: RatedContract,
    private val bestMoviesContract: BestMoviesContract,
    private val locationsContract: LocationsContract,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(RatedViewModel::class.java) -> RatedViewModel(
                    ratedContract,
                )
                isAssignableFrom(UbicacionesViewModel::class.java) -> UbicacionesViewModel(
                    locationsContract,
                )
                isAssignableFrom(MoviesViewModel::class.java) -> MoviesViewModel(
                    bestMoviesContract,
                )
                else -> throw IllegalArgumentException("Unknown ViewModel class you must add it")
            }
        } as T
    }
}