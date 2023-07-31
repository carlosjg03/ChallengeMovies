package com.example.movieschallenge.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.text.SimpleDateFormat

class LocationReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "LocationReceiver"
    }

    private val helper: LocationHelper by lazy { LocationHelper() }

    override fun onReceive(context: Context, intent: Intent) {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val id = System.currentTimeMillis()
        Log.d(TAG, "PING: ${format.format(id)}")
        helper.onReceive(context, goAsync())
        Handler(Looper.getMainLooper()).postDelayed({
            LocationAccess.init(context)
        }, 1000)
    }
}
