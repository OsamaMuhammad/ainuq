package com.app.ainuq.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.ainuq.ui.prescription.PrescriptionItemUiModel

@Database(entities = [PrescriptionItemUiModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun prescriptionDao(): PrescriptionDao
    abstract fun cartDao(): CartDao
}