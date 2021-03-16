package com.app.ainuq.ui.prescription

import androidx.lifecycle.*
import com.app.ainuq.common.AuthStore
import com.app.ainuq.respsitory.UserRepository
import com.app.ainuq.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class AddPrescriptionViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authStore: AuthStore,
    private val userRepository: UserRepository
) : ViewModel() {

//
//    val prescriptions: LiveData<List<PrescriptionItemUiModel>> =
//        userRepository.getAllPrescription("Osamaid").asLiveData()

    private val _messageEvent = MutableLiveData<Event<String>>()
    val messageEvent: LiveData<Event<String>> = _messageEvent


    fun addPrescription(item: PrescriptionItemUiModel) = viewModelScope.launch{
        userRepository.addPrescription(item)
        _messageEvent.value = Event("Prescription Added Successfully")
    }

}