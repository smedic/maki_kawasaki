package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginData(val success: Boolean)

class AuthViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _authState = MutableStateFlow(LoginData(false))
    val authState = _authState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            Log.d("SMEDIC", "login: ")
            authRepository.login()
            delay(2000)
            _authState.update { it.copy(success = true) }
        }
    }
}