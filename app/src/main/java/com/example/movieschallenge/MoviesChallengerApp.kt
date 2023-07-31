package com.example.movieschallenge

import android.app.Application
import android.util.Log
import com.example.movieschallenge.provider.providerModule
import com.example.movieschallenge.save.SaveData
import com.example.movieschallenge.save.SaveDataContract
import com.example.movieschallenge.worker.LocationAccess
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

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
                    module {
                        single<SaveDataContract> { SaveData(baseContext.filesDir) }
                    },
                    providerModule,
                )
            )
            koin.createRootScope()
        }
        LocationAccess.init(applicationContext)
    }
}