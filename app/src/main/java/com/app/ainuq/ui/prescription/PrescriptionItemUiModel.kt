package com.app.ainuq.ui.prescription

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
data class PrescriptionItemUiModel(
    @PrimaryKey val prescriptionId: String,
    val spRight: String,
    val spLeft: String,
    val cylRight: String,
    val cylLeft: String,
    val axisRight: String,
    val axisLeft: String,
    val prismRight: String,
    val prismLeft: String,
    val baseRight: String,
    val baseLeft: String,
    val isSelected: Boolean,
    val userId: String,
    val userName: String,
    val date: String
) : Parcelable {

    @IgnoredOnParcel
    @Ignore
    val id: Long = UUID.randomUUID().mostSignificantBits


}
