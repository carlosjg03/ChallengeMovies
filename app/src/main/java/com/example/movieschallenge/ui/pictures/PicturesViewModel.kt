package com.example.movieschallenge.ui.pictures

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieschallenge.models.Result
import com.example.movieschallenge.services.pictures.PicturesContract
import kotlinx.coroutines.launch

class PicturesViewModel( private val picturesContract: PicturesContract
) : ViewModel() {

    private val _mostrarErrorConsultar = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarErrorConsultar: LiveData<Boolean> = _mostrarErrorConsultar
    private val _mostrarListaVacia = MutableLiveData<Boolean>().apply {
        value = false
    }
    val mostrarListaVacia: LiveData<Boolean> = _mostrarListaVacia
    private val _pictures = MutableLiveData<MutableList<Uri>>().apply {
        value = mutableListOf()
    }
    val pictures: LiveData<MutableList<Uri>> = _pictures

    fun getPictures(){
        viewModelScope.launch {
            picturesContract.getAllDocuments {result->
                when (result){
                    is Result.Success -> {
                        _pictures.postValue(result.value)
                    }
                    is Result.Failure -> _mostrarErrorConsultar.postValue(true)
                }
            }

        }
    }

    fun uploadPicture(pathsPicture: MutableList<Uri>){
        viewModelScope.launch {
            picturesContract.uploadPicture(pathsPicture) {result->
                when (result){
                    is Result.Success -> {
                        _pictures.value?.addAll(result.value)
                        _pictures.postValue(_pictures.value)

                    }
                    is Result.Failure -> _mostrarErrorConsultar.postValue(true)
                }
            }

        }
    }
}