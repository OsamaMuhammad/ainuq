package com.app.ainuq.ui.cart

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@JsonClass(generateAdapter = true)
data class GlassItemUiModel(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val glassId: String,
    val name: String,
    val isSelected: Boolean,
    val price: Double,
) : Parcelable
