package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.AuthRepository
import com.example.myapplication.views.compose.FetchingError
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _authState = MutableStateFlow(LoginData())
    val authState = _authState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            _authState.update { it.copy(showLoading = true) }
            delay(3000)
            authRepository.login().onSuccess {
                _authState.update { it.copy(showLoading = false, navigateToNextScreen = true) }
            }.onFailure {
                _authState.update {
                    it.copy(
                        showLoading = false,
                        snackBarError = FetchingError("Error!")
                    )
                }
            }
        }
    }

    fun snackBarErrorShown() {
        _authState.update { it.copy(snackBarError = null) }
    }
}

data class LoginData(
    val snackBarError: FetchingError? = null,
    val showLoading: Boolean = false,
    val navigateToNextScreen: Boolean = false
)