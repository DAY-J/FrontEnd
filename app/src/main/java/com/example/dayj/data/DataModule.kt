package com.example.dayj.data

import com.example.dayj.data.repo.PlanRepository
import com.example.dayj.data.repo.PlanRepositoryImpl
import com.example.dayj.data.repo.StatisticsRepository
import com.example.dayj.data.repo.StatisticsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun providePlanRepository(planRepositoryImpl: PlanRepositoryImpl): PlanRepository

    @Binds
    @Singleton
    fun provideStatisticsRepository(statisticsRepositoryImpl: StatisticsRepositoryImpl): StatisticsRepository
}

