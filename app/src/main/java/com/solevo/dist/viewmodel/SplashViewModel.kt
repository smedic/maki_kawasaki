package com.solevo.dist.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solevo.dist.data.network.SharedPrefsRepository
import com.solevo.dist.graphs.Graph
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: SharedPrefsRepository,
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Graph.AUTHENTICATION)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            repository.readIsUserLoggedInState().collect { completed ->
                if (completed) {
                    _startDestination.value = Graph.HOME
                } else {
                    _startDestination.value = Graph.AUTHENTICATION
                }
            }
            _isLoading.value = false
        }
    }
}