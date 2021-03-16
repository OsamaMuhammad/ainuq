package com.app.ainuq.ui.productDetail

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@JsonClass(generateAdapter = true)
data class ColorItemUiModel(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val colorId: String,
    val value: String,
    val name: String,
    val isSelected: Boolean
) : Parcelable