package com.mauri.movieapp.presentation.feature

import android.app.Application
import com.mauri.movieapp.foundation.DI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(DI.modules)
        }
    }
}