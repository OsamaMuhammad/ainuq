package com.app.ainuq.ui.productDetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ColorItemUiModel(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val value: String,
    val name: String,
    val isSelected: Boolean
) : Parcelable