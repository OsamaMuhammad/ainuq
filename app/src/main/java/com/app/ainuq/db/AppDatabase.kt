package com.app.ainuq.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.ainuq.ui.cart.CartItemUModel
import com.app.ainuq.ui.prescription.PrescriptionItemUiModel

@Database(entities = [PrescriptionItemUiModel::class, CartItemUModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun prescriptionDao(): PrescriptionDao
    abstract fun cartDao(): CartDao
}