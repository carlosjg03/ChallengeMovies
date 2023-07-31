package com.example.movieschallenge.services.locations

import com.example.movieschallenge.models.LocationModel
import com.example.movieschallenge.models.Result
import com.example.movieschallenge.save.SaveDataContract
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class LocationsImp(
    private val saveDataContract: SaveDataContract
) : LocationsContract{
    override fun invoke(result: (Result<List<LocationModel>>) -> Unit) {
        val db = Firebase.firestore
        val docRef = db.collection("Ubicaciones")
        docRef.get()
            .addOnSuccessListener { document ->
                val locations = document.documents.mapNotNull {
                    it.toObject(LocationModel::class.java)
                }.toList()
                saveDataContract.saveData("Locations.json",Gson().toJson(locations))
                result.invoke(Result.Success(locations))
            }
            .addOnFailureListener { exception ->
                val savedData = saveDataContract.getData("Locations.json")
                if (savedData.isNotBlank()) {
                    val locations = Gson().fromJson<List<LocationModel>>(savedData, (object : TypeToken<MutableList<LocationModel>>() {}).type)
                    result.invoke(Result.Success(locations))
                } else {
                    result.invoke(Result.Failure("No se pudo obtener los puntos"))
                }
            }
    }
}