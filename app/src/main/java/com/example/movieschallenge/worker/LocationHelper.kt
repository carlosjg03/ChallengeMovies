package com.example.movieschallenge.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class LocationHelper : KoinComponent {

    companion object {
        private const val TAG = "LocationHelper"
    }

    fun onReceive(context: Context, result: BroadcastReceiver.PendingResult) {
        Log.d(TAG, "onReceive()")
        LocationTask.fromWorker(context) { location ->
            if (location == null) {
                Log.d(TAG, "location == null")
                result.setResult(0, "FAIL", null)
                result.finish()
                return@fromWorker
            }

            MainScope().launch {
                val db = Firebase.firestore
                val data = hashMapOf(
                    "date" to SimpleDateFormat("dd-MM-yyyy mm:ss", Locale.getDefault()).format(Date()),
                    "lat" to location.latitude,
                    "lng" to location.longitude,
                )
                db.collection("Ubicaciones")
                    .add(data)
                    .addOnSuccessListener { _ ->
                        with(NotificationManagerCompat.from(context)) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                val name = "Generales"
                                val descriptionText = "Uso general de canal"
                                val importance = NotificationManager.IMPORTANCE_DEFAULT
                                val channel = NotificationChannel("default", name, importance).apply {
                                    description = descriptionText
                                }
                                // Register the channel with the system
                                val notificationManager: NotificationManager = (getSystemService(context,NotificationManager::class.java) as NotificationManager)
                                notificationManager.createNotificationChannel(channel)
                            }
                            val builder = NotificationCompat.Builder(context, "default")
                                .setSmallIcon(com.google.android.material.R.drawable.ic_m3_chip_check)
                                .setContentTitle("Ubicación enviada")
                                .setContentText("${location.latitude},${location.longitude}")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            with(NotificationManagerCompat.from(context)) {
                                // notificationId is a unique int for each notification that you must define
                                notify(101, builder.build())
                            }
                        }
                        result.setResult(0, "OK", null)
                        result.finish()
                    }
            }


        }
    }
}