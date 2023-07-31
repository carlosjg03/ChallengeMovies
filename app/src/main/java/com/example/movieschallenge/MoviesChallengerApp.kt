package com.example.movieschallenge

import android.app.Application
import android.util.Log
import com.example.movieschallenge.provider.providerModule
import com.example.movieschallenge.worker.LocationAccess
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoviesChallengerApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Log.e("eeeeee","eeee")
        startKoin {
            androidLogger()
            androidContext(baseContext)
            androidFileProperties()
            koin.loadModules(
                arrayListOf(
                    providerModule
                )
            )
            koin.createRootScope()
        }
        LocationAccess.init(applicationContext)
    }
}