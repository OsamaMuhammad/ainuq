package com.app.ainuq.ui.orders

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.ainuq.ui.cart.CartItemUiModel
import java.util.*

@Entity
data class OrderItemUiModel(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    @PrimaryKey val orderItemId: String,
    val items: List<CartItemUiModel>,
    val address: String,
    val orderStatus: String,
    val deliveryType: String
)
