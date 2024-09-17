package com.dayj.dayj.di

import android.content.Context
import com.dayj.dayj.data.PreferenceManager
import com.dayj.dayj.friends.data.source.FriendsDataSource
import com.dayj.dayj.friends.data.source.FriendsDataSourceImpl
import com.dayj.dayj.friends.domain.repository.FriendsRepository
import com.dayj.dayj.friends.domain.repository.FriendsRepositoryImpl
import com.dayj.dayj.lounge.data.source.LoungeDataSource
import com.dayj.dayj.lounge.data.source.LoungeDataSourceImpl
import com.dayj.dayj.lounge.domain.repository.LoungeRepository
import com.dayj.dayj.lounge.domain.repository.LoungeRepositoryImpl
import com.dayj.dayj.mypage.data.UserDataSource
import com.dayj.dayj.mypage.data.UserDataSourceImpl
import com.dayj.dayj.network.ApiService
import com.dayj.dayj.network.api.PlanOptionService
import com.dayj.dayj.network.api.PlanService
import com.dayj.dayj.network.api.StatisticsService
import com.dayj.dayj.network.api.adapter.StatisticsDateJsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProviders {
    private const val BASE_URL = "http://35.216.13.139:8080"
    private val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(StatisticsDateJsonAdapter())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()


    @Singleton
    @Provides
    @EtcRetrofit
    fun provideEtcRetrofit(
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
    }

    @Singleton
    @Provides
    @OriginalRetrofit
    fun provideOriginalRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("http://35.216.13.139:8080/api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(
        @OriginalRetrofit retrofit: Retrofit
    ): ApiService = retrofit.create(ApiService::class.java)


    @Singleton
    @Provides
    fun providePlanService(@EtcRetrofit retrofit: Retrofit): PlanService =
        retrofit.create(PlanService::class.java)

    @Singleton
    @Provides
    fun providePlanOptionService(@EtcRetrofit retrofit: Retrofit): PlanOptionService =
        retrofit.create(PlanOptionService::class.java)

    @Singleton
    @Provides
    fun provideStatisticsService(@EtcRetrofit retrofit: Retrofit): StatisticsService =
        retrofit.create(StatisticsService::class.java)

    @Provides
    @Singleton
    fun providePreferenceManager(
        @ApplicationContext context: Context
    ): PreferenceManager = PreferenceManager(context)

    @Provides
    @Singleton
    fun provideFriendsDataSource(
        apiService: ApiService,
        preferenceManager: PreferenceManager
    ): FriendsDataSource = FriendsDataSourceImpl(
        apiService = apiService,
        preferenceManager = preferenceManager
    )

    @Provides
    @Singleton
    fun provideGroupGoalEdit(
        friendsDataSource: FriendsDataSource
    ): FriendsRepository = FriendsRepositoryImpl(friendsDataSource = friendsDataSource)

    @Provides
    @Singleton
    fun provideLoungeDataSource(
        apiService: ApiService
    ): LoungeDataSource = LoungeDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideLoungeRepository(
        loungeDataSource: LoungeDataSource,
        preferenceManager: PreferenceManager
    ): LoungeRepository = LoungeRepositoryImpl(
        loungeDataSource = loungeDataSource,
        preferenceManager = preferenceManager
    )

    @Provides
    @Singleton
    fun provideUserDataSource(
        apiService: ApiService,
        preferenceManager: PreferenceManager
    ): UserDataSource = UserDataSourceImpl(
        apiService = apiService,
        preferenceManager = preferenceManager
    )
}