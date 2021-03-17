package com.app.ainuq.ui.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.ainuq.common.AuthStore
import com.app.ainuq.ui.profile.UserItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authStore: AuthStore
) : ViewModel() {

    fun login(){
        authStore.user = UserItemUiModel(
            firstName = "Osama",
            lastName = "Muhammad",
            userId = "Osamaid",
            email = "osamamuhammad92@gmail.com",
            phoneNumber = "+923422363318"
        )
    }
}