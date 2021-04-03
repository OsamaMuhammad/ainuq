package com.app.ainuq.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.ainuq.ui.cart.CartItemUiModel
import com.app.ainuq.ui.orders.OrderItemUiModel
import com.app.ainuq.ui.prescription.PrescriptionItemUiModel

@Database(entities = [PrescriptionItemUiModel::class, CartItemUiModel::class, OrderItemUiModel::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun prescriptionDao(): PrescriptionDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
}