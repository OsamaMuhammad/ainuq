package com.app.ainuq.ui.signup

import androidx.lifecycle.*
import com.app.ainuq.common.AuthStore
import com.app.ainuq.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authStore: AuthStore
): ViewModel() {


    private val _eventState = MutableLiveData<Result<Unit>>()
    val eventState : LiveData<Result<Unit>> = _eventState

    fun signUp(firstName: String, lastName: String, email: String, phoneNumber: String, password: String){
        _eventState.value = Result.Loading
        viewModelScope.launch {
            delay(2000)
            _eventState.value = Result.Success(data = Unit, message = "Account created successfully")
        }
    }

}