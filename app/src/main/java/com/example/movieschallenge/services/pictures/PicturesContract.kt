package com.example.movieschallenge.services.pictures

import android.net.Uri
import com.example.movieschallenge.models.Result

interface PicturesContract {
    fun uploadPicture(path: MutableList<Uri>, result: (Result<MutableList<Uri>>)->Unit)
    fun getAllDocuments(result: (Result<MutableList<Uri>>)->Unit)
}