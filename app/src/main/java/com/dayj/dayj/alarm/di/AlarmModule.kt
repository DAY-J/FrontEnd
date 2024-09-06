package com.dayj.dayj.alarm.di

import android.content.Context
import com.dayj.dayj.alarm.AlarmCenter
import com.dayj.dayj.alarm.AlarmCenterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {

    @Provides
    @Singleton
    fun provideAlarmCenter(
        @ApplicationContext context: Context
    ): AlarmCenter = AlarmCenterImpl(context)

}