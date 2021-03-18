package com.app.ainuq.ui.login

import androidx.lifecycle.*
import com.app.ainuq.common.AuthStore
import com.app.ainuq.ui.profile.UserItemUiModel
import com.app.ainuq.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authStore: AuthStore
) : ViewModel() {

    private val _eventState = MutableLiveData<Result<Unit>>()
    val eventState : LiveData<Result<Unit>> = _eventState

    fun login(email: String, password: String){
        _eventState.value = Result.Loading

        viewModelScope.launch {
            delay(2000)
            authStore.user = UserItemUiModel(
                firstName = "Osama",
                lastName = "Muhammad",
                userId = "Osamaid",
                email = "osamamuhammad92@gmail.com",
                phoneNumber = "+923422363318"
            )
            _eventState.value = Result.Success(data = Unit, message = "Account logged in successfully")
        }

    }
}