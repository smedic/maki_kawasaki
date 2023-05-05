package com.example.myapplication.views.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> UiStateContent(
    paddingValues: PaddingValues,
    uiState: UiState<T>,
    loadingContent: @Composable () -> Unit = {
        FullScreenLoading()
    },
    retryCallback: () -> Unit,
    failureContent: @Composable (FetchingError?) -> Unit = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            ErrorView(
                it,
                retryCallback
            )
        }
    },
    successContent: @Composable (T) -> Unit
) {
    Box(modifier = Modifier.padding(paddingValues)) {
        when (uiState) {
            is UiState.Loading -> {
                loadingContent()
            }

            is UiState.Error -> {
                failureContent(uiState.error)
            }

            is UiState.Success -> {
                if (uiState.data == null) {
                    failureContent(FetchingError())
                } else {
                    successContent(uiState.data)
                }
            }
        }
    }
}
