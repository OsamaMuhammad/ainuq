package com.app.ainuq.respsitory

import com.app.ainuq.db.PrescriptionDao
import com.app.ainuq.ui.prescription.PrescriptionItemUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val prescriptionDao: PrescriptionDao
) {


    suspend fun addPrescription(item: PrescriptionItemUiModel) =
        withContext(Dispatchers.IO) {
            prescriptionDao.insertPrescription(item)
        }


    fun getAllPrescription(userId: String) =
        prescriptionDao.getAllPrescriptionByUserId(userId)

    suspend fun selectDeselectAllPrescription(isSelected: Boolean) =
        withContext(Dispatchers.IO) {
            prescriptionDao.selectDeselectAll(isSelected)
        }

    suspend fun getPrescriptionByPrescriptionId(prescriptionId: String): List<PrescriptionItemUiModel> =
        withContext(Dispatchers.IO) {
            prescriptionDao.getAllPrescriptionByPrescriptionId(prescriptionId)
        }


    suspend fun selectDeselectPrescription(isSelected: Boolean, prescriptionId: String) =
        withContext(Dispatchers.IO) {
            prescriptionDao.selectDeselectItem(isSelected, prescriptionId)
        }


}