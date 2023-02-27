package com.dersarco.appwriteexample

import android.app.Application
import com.dersarco.appwriteexample.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            AndroidLogger()
            modules(appModule)
        }
    }
}
