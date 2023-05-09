package com.solevo.dist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.PhoneAuthProvider
import com.solevo.dist.data.network.AuthRepository
import com.solevo.dist.data.network.SharedPrefsRepository
import com.solevo.dist.extensions.toSha1
import com.solevo.dist.views.compose.FetchingError
import com.solevo.dist.views.compose.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val sharedPrefRepository: SharedPrefsRepository,
) : ViewModel() {

    lateinit var phoneNumber: String
    lateinit var verificationId: String
    lateinit var forceResendingToken: PhoneAuthProvider.ForceResendingToken

    private val _authState = MutableStateFlow(LoginData())
    val authState = _authState.asStateFlow()

    private val _pinState = MutableStateFlow<UiState<String>?>(null)
    val pinState = _pinState.asStateFlow()

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

    fun createPINCode(pin: String) {
        _pinState.value = UiState.Loading
        viewModelScope.launch {
            val sha1PinCode = pin.toSha1()
            sharedPrefRepository.saveUserPINCode(sha1PinCode)
            _pinState.value = UiState.Success(sha1PinCode)
        }
    }
}

data class LoginData(
    val snackBarError: FetchingError? = null,
    val showLoading: Boolean = false,
    val navigateToNextScreen: Boolean = false,
)