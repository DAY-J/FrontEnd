package com.dayj.dayj

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DayJApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}