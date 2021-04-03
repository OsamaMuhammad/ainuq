package com.app.ainuq.db

import androidx.room.*
import com.app.ainuq.ui.orders.OrderItemUiModel
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert
    fun insertOrderItem(item: OrderItemUiModel)

    @Query("SELECT * FROM OrderItemUiModel")
    fun getOrderItems(): Flow<List<OrderItemUiModel>>

    @Delete
    fun deleteOrderItem(item: OrderItemUiModel)

    @Query("DELETE FROM OrderItemUiModel WHERE orderItemId = :OrderItemId")
    fun deleteByOrderItemId(OrderItemId: String)

    @Update
    fun updateOrderItem(item: OrderItemUiModel)

    @Query("DELETE FROM OrderItemUiModel")
    fun deleteAllItems()
}