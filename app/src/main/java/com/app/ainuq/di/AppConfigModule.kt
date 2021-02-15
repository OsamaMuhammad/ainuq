package com.app.ainuq.di

import com.app.ainuq.common.AppConfig
import com.app.ainuq.common.AppConfigImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppConfigModule {

    @Singleton
    @Binds
    abstract fun bindAppConfig(appConfig: AppConfigImplementation): AppConfig

}