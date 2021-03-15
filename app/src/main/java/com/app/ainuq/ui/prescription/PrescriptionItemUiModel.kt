package com.app.ainuq.ui.prescription

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PrescriptionItemUiModel(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val prescriptionId: String,
    val spRight: Double,
    val spLeft: Double,
    val cylRight: Double,
    val cylLeft: Double,
    val axisRight: Double,
    val axisLeft: Double,
    val prismRight: Double,
    val prismLeft: Double,
    val baseRight: Double,
    val baseLeft: Double,
    val isSelected: Boolean
) : Parcelable
