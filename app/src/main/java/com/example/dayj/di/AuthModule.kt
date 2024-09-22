package com.example.dayj.di

import com.example.dayj.data.repo.LoginAuthorizationRepository
import com.example.dayj.data.repo.LoginAuthorizationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    fun provideLoginAuthorizationRepository(loginAuthorizationRepositoryImpl: LoginAuthorizationRepositoryImpl): LoginAuthorizationRepository
}