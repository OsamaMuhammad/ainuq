package com.app.ainuq.respsitory

import com.app.ainuq.db.AppDatabase
import com.app.ainuq.db.PrescriptionDao
import com.app.ainuq.ui.prescription.PrescriptionItemUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val appDatabase: AppDatabase,
) {


    suspend fun addPrescription(item: PrescriptionItemUiModel) =
        withContext(Dispatchers.IO){
            appDatabase.prescriptionDao().insertPrescription(item)
        }


    fun getAllPrescription(userId: String) =
            appDatabase.prescriptionDao().getAllPrescriptionByUserId(userId)

    suspend fun selectDeselectAllPrescription(isSelected: Boolean) =
        withContext(Dispatchers.IO){
            appDatabase.prescriptionDao().selectDeselectAll(isSelected)
        }


    suspend fun selectDeselectPrescription(isSelected: Boolean, prescriptionId: String) =
        withContext(Dispatchers.IO){
            appDatabase.prescriptionDao().selectDeselectItem(isSelected, prescriptionId)
        }


}