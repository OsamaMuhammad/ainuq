package com.app.ainuq.ui.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class GlassItemUiModel(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val glassId: String,
    val name: String,
    val isSelected: Boolean,
    val price: String,
) : Parcelable
