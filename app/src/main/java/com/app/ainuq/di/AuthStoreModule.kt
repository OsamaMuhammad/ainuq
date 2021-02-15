package com.app.ainuq.di

import com.app.ainuq.common.AuthStore
import com.app.ainuq.common.SharedPreferencesAuthStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class AuthStoreModule {

    @Singleton
    @Binds
    abstract fun bindAuthStore(sharedPreferencesAuthStore: SharedPreferencesAuthStore): AuthStore
}