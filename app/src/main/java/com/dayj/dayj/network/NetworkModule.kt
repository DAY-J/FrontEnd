package com.dayj.dayj.network


import com.dayj.dayj.network.api.PlanOptionService
import com.dayj.dayj.network.api.PlanService
import com.dayj.dayj.network.api.StatisticsService
import com.dayj.dayj.network.api.adapter.StatisticsDateJsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://35.216.13.139:8080"
    private const val BASE_URL2 = "https://port-0-backend-lxx8tpse0e5176b1.sel5.cloudtype.app"

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(StatisticsDateJsonAdapter())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        moshi: Moshi
    ): Retrofit {

        return Retrofit.Builder().baseUrl(BASE_URL2)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
    }

    @Singleton
    @Provides
    fun providePlanService(retrofit: Retrofit): PlanService =
        retrofit.create(PlanService::class.java)

    @Singleton
    @Provides
    fun providePlanOptionService(retrofit: Retrofit): PlanOptionService =
        retrofit.create(PlanOptionService::class.java)

    @Singleton
    @Provides
    fun provideStatisticsService(retrofit: Retrofit): StatisticsService =
        retrofit.create(StatisticsService::class.java)
}