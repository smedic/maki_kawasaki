package com.example.myapplication.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.viewmodel.HomeContent
import com.example.myapplication.viewmodel.HomeViewModel
import com.example.myapplication.views.compose.UiStateContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        Modifier.padding(16.dp),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Action") },
                onClick = { viewModel.fetchDataWithError() }
            )
        },
    ) { paddingValues ->
        UiStateContent(
            paddingValues = paddingValues,
            uiState = state.uiState,
            retryCallback = { viewModel.fetchData() }) { content ->
            HomePage(content)
        }
    }
}

@Composable
fun HomePage(content: HomeContent) {
    Text(
        content.title,
    )
}