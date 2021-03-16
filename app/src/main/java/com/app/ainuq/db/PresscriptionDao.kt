package com.app.ainuq.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.ainuq.ui.prescription.PrescriptionItemUiModel
import kotlinx.coroutines.flow.Flow


@Dao
interface PrescriptionDao {

    @Insert
    fun insertPrescription(item: PrescriptionItemUiModel)


    @Query("SELECT * FROM PrescriptionItemUiModel WHERE userId = (:userId)")
    fun getAllPrescriptionByUserId(userId: String): Flow<List<PrescriptionItemUiModel>>

    @Query("SELECT * FROM PrescriptionItemUiModel WHERE prescriptionId = (:prescriptionId)")
    fun getAllPrescriptionByPrescriptionId(prescriptionId: String): List<PrescriptionItemUiModel>

    @Query("UPDATE PrescriptionItemUiModel SET isSelected = (:isSelected)")
    fun selectDeselectAll(isSelected: Boolean)


    @Query("UPDATE PrescriptionItemUiModel SET isSelected = (:isSelected) WHERE prescriptionId = (:prescriptionId)")
    fun selectDeselectItem(isSelected: Boolean, prescriptionId: String)


}