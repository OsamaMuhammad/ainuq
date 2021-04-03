package com.app.ainuq.respsitory

import com.app.ainuq.db.OrderDao
import com.app.ainuq.ui.orders.OrderItemUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderDao: OrderDao
) {
    suspend fun addToOrder(item: OrderItemUiModel) = withContext(Dispatchers.IO) {
        orderDao.insertOrderItem(item)
    }

    suspend fun updateOrderItem(item: OrderItemUiModel) = withContext(Dispatchers.IO) {
        orderDao.updateOrderItem(item)
    }

    fun getOrderItems() =orderDao.getOrderItems()

    suspend fun deleteOrderItemById(OrderItemId: String) = withContext(Dispatchers.IO) {
        orderDao.deleteByOrderItemId(OrderItemId)
    }

    suspend fun deleteOrderItem(OrderItem: OrderItemUiModel) = withContext(Dispatchers.IO) {
        orderDao.deleteOrderItem(item = OrderItem)
    }

    suspend fun deleteAllItems() = withContext(Dispatchers.IO) {
        orderDao.deleteAllItems()
    }
}