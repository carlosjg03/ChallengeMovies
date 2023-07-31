package com.example.movieschallenge.ui.notifications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieschallenge.models.LocationModel
import com.example.movieschallenge.services.locations.LocationsContract
import kotlinx.coroutines.launch
import com.example.movieschallenge.models.Result

class NotificationsViewModel(
    private val locationsContract: LocationsContract
) : ViewModel() {

    private val _mostrarErrorConsultar = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarErrorConsultar: LiveData<Boolean> = _mostrarErrorConsultar
    private val _mostrarListaVacia = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarListaVacia: LiveData<Boolean> = _mostrarListaVacia
    private val _locations = MutableLiveData<List<LocationModel>>().apply {
        value = listOf()
    }
    val locations: LiveData<List<LocationModel>> = _locations

    fun getLocations(){
        viewModelScope.launch {
            locationsContract.invoke {result->
                when (result){
                    is Result.Success -> {
                        Log.e("test:",result.value.toString())
                        _locations.postValue(result.value)
                        _mostrarListaVacia.postValue(result.value.isEmpty())
                    }
                    is Result.Failure -> _mostrarErrorConsultar.postValue(true)
                }
            }

        }
    }
}