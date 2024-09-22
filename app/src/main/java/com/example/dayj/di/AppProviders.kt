package com.example.dayj.di

import android.content.Context
import com.example.dayj.data.PreferenceManager
import com.example.dayj.data.repo.AuthRepository
import com.example.dayj.data.repo.AuthRepositoryImpl
import com.example.dayj.data.repo.LoginAuthorizationRepository
import com.example.dayj.data.repo.UserRepository
import com.example.dayj.data.repo.UserRepositoryImpl
import com.example.dayj.datastore.SelfUserAccountDataStore
import com.example.dayj.datastore.SelfUserAccountDataStoreImpl
import com.example.dayj.network.ApiService
import com.example.dayj.network.api.AuthService
import com.example.dayj.network.api.PlanOptionService
import com.example.dayj.network.api.PlanService
import com.example.dayj.network.api.StatisticsService
import com.example.dayj.network.api.UserService
import com.example.dayj.network.api.adapter.StatisticsDateJsonAdapter
import com.example.dayj.network.util.AuthAuthenticator
import com.example.dayj.network.util.RequestInterceptor
import com.example.dayj.ui.friends.data.source.FriendsDataSource
import com.example.dayj.ui.friends.data.source.FriendsDataSourceImpl
import com.example.dayj.ui.friends.domain.repository.FriendsRepository
import com.example.dayj.ui.friends.domain.repository.FriendsRepositoryImpl
import com.example.dayj.ui.lounge.data.source.LoungeDataSource
import com.example.dayj.ui.lounge.data.source.LoungeDataSourceImpl
import com.example.dayj.ui.lounge.domain.repository.LoungeRepository
import com.example.dayj.ui.lounge.domain.repository.LoungeRepositoryImpl
import com.example.dayj.ui.mypage.data.UserDataSource
import com.example.dayj.ui.mypage.data.UserDataSourceImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
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
    const val BASE_URL = "http://35.216.13.139:8080"
    private val loggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
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
    fun provideRequestInterceptor(loginAuthorizationRepository: LoginAuthorizationRepository): Interceptor =
        RequestInterceptor(loginAuthorizationRepository)

    @Provides
    @Singleton
    @EtcRetrofit
    fun provideEtcOkHttpClientBuilder(
        interceptor: Interceptor,
        authenticator: AuthAuthenticator
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
            authenticator(authenticator)
        }.build()

    @Provides
    @Singleton
    @OriginalRetrofit
    fun provideOriginalOkHttpClientBuilder(
        interceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
        }.build()


    @Singleton
    @Provides
    @EtcRetrofit
    fun provideEtcRetrofit(
        moshi: Moshi,
        @EtcRetrofit okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
    }

    @Singleton
    @Provides
    @OriginalRetrofit
    fun provideOriginalRetrofit(
        @OriginalRetrofit okHttpClient: OkHttpClient
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


    @Singleton
    @Provides
    fun provideAuthService(@EtcRetrofit retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)


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
    fun provideSelfUserAccountDataStore(@ApplicationContext context: Context): SelfUserAccountDataStore {
        return SelfUserAccountDataStoreImpl(context)
    }

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
    fun provideAuthRepository(
        authService: AuthService,
    ): AuthRepository = AuthRepositoryImpl(authService)

    @Provides
    @Singleton
    fun provideUserService(
        @EtcRetrofit retrofit: Retrofit
    ): UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(
        userService: UserService
    ): UserRepository =
        UserRepositoryImpl(userService)


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