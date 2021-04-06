package com.app.ainuq.ui.orders

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.app.ainuq.ui.cart.CartItemUiModel
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity
data class OrderItemUiModel(
    @PrimaryKey val orderItemId: String,
    val items: List<CartItemUiModel>,
    val address: String,
    val orderStatus: String,
    val deliveryType: String
) : Parcelable{

    @Ignore
    @IgnoredOnParcel
    val id: Long = UUID.randomUUID().mostSignificantBits
}
