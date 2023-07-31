package com.example.movieschallenge.services.locations

import com.example.movieschallenge.models.LocationModel
import com.example.movieschallenge.models.Result

interface LocationsContract {
    operator fun invoke(result:(Result<List<LocationModel>>)->Unit)
}