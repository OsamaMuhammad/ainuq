package com.app.ainuq.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.app.ainuq.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    private val databaseName ="Ainuq-DB"

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, databaseName
        ).build()
    }

}