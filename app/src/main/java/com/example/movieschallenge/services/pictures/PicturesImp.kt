package com.example.movieschallenge.services.pictures

import android.net.Uri
import com.example.movieschallenge.models.Result
import com.google.android.gms.tasks.Tasks
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class PicturesImp:PicturesContract {
    override fun uploadPicture(uri: MutableList<Uri>, result: (Result<MutableList<Uri>>)->Unit){
        val storageRef = Firebase.storage("gs://movieschallenge-c0475.appspot.com").getReference()

        val taskReload = mutableListOf<StorageReference>()
        val tasks = uri.map{
            val riversRef = storageRef.child("pictures/${it.lastPathSegment}")
            taskReload.add(riversRef)
            riversRef.putFile(it)
        }

        Tasks.whenAllComplete(tasks).addOnFailureListener {
            result.invoke(Result.Failure("No se pudo obtener los puntos"))
        }.addOnSuccessListener { taskSnapshot ->
            val map = taskReload.map { it.downloadUrl }
            Tasks.whenAllComplete(map).addOnFailureListener {
                result.invoke(Result.Failure("No se pudo obtener los puntos"))
            }.addOnSuccessListener { taskSnapshot ->
                result.invoke(Result.Success(map.map { it.result }.toMutableList()))
            }
        }
    }

    override fun getAllDocuments(result: (Result<MutableList<Uri>>)->Unit){
        val storageRef = Firebase.storage("gs://movieschallenge-c0475.appspot.com").getReference()
        try {
            storageRef.child("pictures").listAll() .addOnSuccessListener{
                val map = it.items.map { it.downloadUrl }
                Tasks.whenAllComplete(map).addOnSuccessListener{
                    result.invoke(Result.Success(map.map { it.result }.toMutableList()))
                }.addOnFailureListener{
                    result.invoke(Result.Failure("No se pudo obtener los puntos"))
                }
            }.addOnFailureListener{
                result.invoke(Result.Failure("No se pudo obtener los puntos"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}