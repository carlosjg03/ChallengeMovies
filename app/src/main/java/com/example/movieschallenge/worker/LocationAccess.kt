package com.example.movieschallenge.worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import java.util.Calendar
import java.util.GregorianCalendar

object LocationAccess {

    private const val TAG = "LocationAccess"
    private const val TIME_NEXT = 5 // NOTA: ¡Que sea divisible entre 60! (2, 3, 5, 10, 15, 20, 30)

    fun init(context: Context) {
        Log.d(TAG, "init()")
        cancel(context)
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, LocationReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        }
        alarmMgr.set(AlarmManager.RTC_WAKEUP, nextCallMillis(), alarmIntent)
        context.packageManager.setComponentEnabledSetting(
            ComponentName(
                context,
                LocationReceiver::class.java
            ), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
        )
    }

    fun cancel(context: Context) {
        Log.d(TAG, "cancel()")
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarmIntent = Intent(context, LocationReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        }

        alarmMgr.cancel(alarmIntent)
        context.packageManager.setComponentEnabledSetting(
            ComponentName(
                context,
                LocationReceiver::class.java
            ), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
    }

    private fun nextCallMillis() : Long {
        val current = GregorianCalendar()
        current.add(Calendar.MINUTE, 5)
        return current.timeInMillis
    }
}