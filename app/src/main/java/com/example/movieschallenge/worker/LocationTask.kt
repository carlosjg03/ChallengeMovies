package com.example.movieschallenge.worker

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.*


object LocationTask {
    private const val TAG = "LocationTask"
    @SuppressLint("MissingPermission")
    fun fromWorker(context: Context, doneLocation: (Location?) -> Unit) {
        try {
            val callback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    Log.d(TAG, "lastLocation= ${p0.lastLocation}")
                    doneLocation(p0.lastLocation)
                }

                override fun onLocationAvailability(p0: LocationAvailability) {
                    if (!p0.isLocationAvailable) {
                        Log.d(TAG, "lastLocation= null")
                        doneLocation(null)
                    }
                }
            }

            val client = LocationServices.getFusedLocationProviderClient(context.applicationContext)

            client.lastLocation.addOnSuccessListener { location: Location? ->
                when {
                    location != null -> {
                        Log.d(TAG, "lastLocation= $location")
                        doneLocation(location)
                    }
                    else -> {
                        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,60_000).apply {
                            setMaxUpdates(1)
                        }.build()
                        request.setFastestInterval(20_000)
                        request.setExpirationDuration(60_000)
                        client.requestLocationUpdates(request, callback, Looper.getMainLooper())
                    }
                }
            }
        }

        catch (ex: Exception) {
            Log.e(TAG, "Error de ubicación:", ex)
            doneLocation(null)
        }
    }
}