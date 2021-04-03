package com.app.ainuq.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.app.ainuq.db.AppDatabase
import com.app.ainuq.db.CartDao
import com.app.ainuq.db.OrderDao
import com.app.ainuq.db.PrescriptionDao
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

    @Singleton
    @Provides
    fun provideCartDao(appDatabase: AppDatabase): CartDao {
        return appDatabase.cartDao()
    }


    @Singleton
    @Provides
    fun provideOrderDao(appDatabase: AppDatabase): OrderDao {
        return appDatabase.orderDao()
    }

    @Singleton
    @Provides
    fun providePrescriptionDao(appDatabase: AppDatabase): PrescriptionDao {
        return appDatabase.prescriptionDao()
    }

}