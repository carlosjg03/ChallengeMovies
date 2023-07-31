package com.example.movieschallenge.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieschallenge.services.best_movies.BestMoviesContract

class DashboardViewModel(
    private val bestMoviesContract: BestMoviesContract
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}