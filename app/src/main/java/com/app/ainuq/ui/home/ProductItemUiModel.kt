package com.app.ainuq.ui.home

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import com.app.ainuq.ui.cart.GlassItemUiModel
import com.app.ainuq.ui.prescription.PrescriptionItemUiModel
import com.app.ainuq.ui.productDetail.ColorItemUiModel
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
data class ProductItemUiModel(
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
    val glasses: List<GlassItemUiModel>,
    var prescription: PrescriptionItemUiModel? = null
) : Parcelable{

    @Ignore
    @IgnoredOnParcel
    val id: Long = UUID.randomUUID().mostSignificantBits

}

data class HomeUiModel(
    val listCategory: List<CategoryItemUiModel>,
    val listPopular: List<ProductItemUiModel>,
    val listYouMayLike: List<ProductItemUiModel>,
)