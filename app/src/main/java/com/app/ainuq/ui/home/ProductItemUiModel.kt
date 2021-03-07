package com.app.ainuq.ui.home

import android.os.Parcelable
import com.app.ainuq.ui.productDetail.ColorItemUiModel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ProductItemUiModel(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val productId: String,
    val name: String,
    val price: String,
    val isFavourite: Boolean,
    val rating: String,
    val gender: String,
    val weight: String,
    val material: String,
    val thickness: String,
    val images: List<String>,
    val colors: List<ColorItemUiModel>,
) : Parcelable

data class HomeUiModel(
    val listCategory: List<CategoryItemUiModel>,
    val listPopular: List<ProductItemUiModel>,
    val listYouMayLike: List<ProductItemUiModel>,
)