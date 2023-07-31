package com.example.movieschallenge.services.locations

import android.util.Log
import com.example.movieschallenge.models.LocationModel
import com.example.movieschallenge.models.Result
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LocationsImp : LocationsContract{
    override fun invoke(result: (Result<List<LocationModel>>) -> Unit) {
        val db = Firebase.firestore
        val docRef = db.collection("Ubicaciones")
        docRef.get()
            .addOnSuccessListener { document ->
                result.invoke(Result.Success(document.documents.mapNotNull {
                Log.e("test:",it.data.toString())
                    it.toObject(LocationModel::class.java)
                }.toList()))
            }
            .addOnFailureListener { exception ->
                result.invoke(Result.Failure("No se pudo obtener los puntos"))
            }
    }
}