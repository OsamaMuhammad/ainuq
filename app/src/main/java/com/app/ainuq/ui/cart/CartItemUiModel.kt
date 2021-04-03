package com.app.ainuq.ui.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.ainuq.ui.productDetail.ColorItemUiModel
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class CartItemUiModel(
    @PrimaryKey val cartItemId: String,
    val productId: String,
    val name: String,
    val price: Double,
    val isFavourite: Boolean,
    val rating: String,
    val gender: String,
    val weight: String,
    val material: String,
    val thickness: String,
    val images: List<String>,
    val colors: List<ColorItemUiModel>,
    val glasses: List<GlassItemUiModel>,
    var prescriptionId: String? = null,
    var modelUrl: String,
)
