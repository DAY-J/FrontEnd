package com.dayj.dayj

import android.app.Application
import com.dayj.dayj.data.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class DayJApplication : Application() {
    @Inject
    lateinit var preferenceManager: PreferenceManager
    override fun onCreate() {
        super.onCreate()
        preferenceManager.putUserId(userId = 1)
        preferenceManager.putUserName(name = "qwdas")
    }
}