package com.example.movieschallenge.provider

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieschallenge.services.best_movies.BestMoviesContract
import com.example.movieschallenge.services.locations.LocationsContract
import com.example.movieschallenge.services.rated.RatedContract
import com.example.movieschallenge.ui.dashboard.DashboardViewModel
import com.example.movieschallenge.ui.home.HomeViewModel
import com.example.movieschallenge.ui.notifications.NotificationsViewModel

class ViewModelFactory(
    private val ratedContract: RatedContract,
    private val bestMoviesContract: BestMoviesContract,
    private val locationsContract: LocationsContract,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(
                    ratedContract,
                )
                isAssignableFrom(NotificationsViewModel::class.java) -> NotificationsViewModel(
                    locationsContract,
                )
                isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(
                    bestMoviesContract,
                )
                else -> throw IllegalArgumentException("Unknown ViewModel class you must add it")
            }
        } as T
    }
}