package com.solevo.dist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solevo.dist.views.compose.FetchingError
import com.solevo.dist.views.compose.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState(UiState.Loading))
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _state.update { it.copy(uiState = UiState.Loading) }
            delay(2000)
            _state.update { it.copy(uiState = UiState.Success(HomeContent("Logged in successfully!"))) }
        }
    }

    fun fetchDataWithError() {
        viewModelScope.launch {
            _state.update { it.copy(uiState = UiState.Loading) }
            delay(1000)
            _state.update {
                it.copy(
                    uiState = UiState.Error(
                        FetchingError(
                            "Error",
                            "Something nasty..."
                        )
                    )
                )
            }
        }
    }
}

data class HomeScreenState(
    val uiState: UiState<HomeContent>,
)

data class HomeContent(
    val title: String,
)