package com.example.myapplication.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.extensions.showMessage
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.views.compose.FullScreenLoading
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginContent(
    onClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit,
    onDesignSystemClick: () -> Unit,
    viewModel: AuthViewModel = koinViewModel()
) {
    val content by viewModel.authState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { androidx.compose.material3.Text(text = "Login") },
                actions = {
                    IconButton(onClick = { onDesignSystemClick() }) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
                    }
                },
            )
        }
    ) { paddingValues ->

        val context = LocalContext.current

        LaunchedEffect(key1 = content.snackBarError) {
            content.snackBarError?.run {
                snackbarHostState.showMessage(
                    error = this,
                    context = context
                )
                viewModel.snackBarErrorShown()
            }
        }

        LaunchedEffect(key1 = content.navigateToNextScreen) {
            if (content.navigateToNextScreen) {
                onClick()
            }
        }

        if (content.showLoading) {
            FullScreenLoading()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.clickable {
                        viewModel.login()
                    },
                    text = "LOGIN",
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.clickable { onSignUpClick() },
                    text = "Sign Up",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    modifier = Modifier.clickable { onForgotClick() },
                    text = "Forgot Password",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}