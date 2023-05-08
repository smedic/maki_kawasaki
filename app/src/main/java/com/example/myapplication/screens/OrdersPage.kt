package com.example.myapplication.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.viewmodel.HomeContent
import com.example.myapplication.viewmodel.HomeViewModel
import com.example.myapplication.views.compose.UiStateContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersPage(
    onDesignSystemClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Action") },
                onClick = { viewModel.fetchDataWithError() }
            )
        },
        topBar = {
            TopAppBar(
                title = { Text(text = "Orders") },
                actions = {
                    IconButton(onClick = { onDesignSystemClick() }) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
                    }
                },
            )
        }
    ) { paddingValues ->
        UiStateContent(
            paddingValues = paddingValues,
            uiState = state.uiState,
            retryCallback = { viewModel.fetchData() }) { content ->
            OrdersContent(content)
        }
    }
}

@Composable
fun OrdersContent(content: HomeContent) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            content.title,
        )
    }
}