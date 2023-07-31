package com.example.movieschallenge.models

import com.google.gson.annotations.SerializedName

data class LocationModel(
    @SerializedName("date") val date:String="",
    @SerializedName("lat") val lat:Double=0.0,
    @SerializedName("lng") val lng:Double=0.0
)
