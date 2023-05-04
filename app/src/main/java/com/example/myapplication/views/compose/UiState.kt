package com.example.myapplication.views.compose

sealed class UiState<out R> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T?) : UiState<T>()
    data class Error(
        val error: FetchingError?
    ) : UiState<Nothing>()

    fun getSuccessData() = (this as? Success)?.data
}
