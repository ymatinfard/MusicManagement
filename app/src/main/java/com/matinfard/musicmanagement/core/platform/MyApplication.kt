package com.matinfard.musicmanagement.core.platform

import android.app.Application
import com.matinfard.musicmanagement.BuildConfig
import com.matinfard.musicmanagement.core.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(applicationModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        //    LeakCanary.install(this)
        }

    }

}