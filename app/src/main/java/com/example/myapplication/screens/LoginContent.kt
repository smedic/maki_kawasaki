package com.example.myapplication.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.viewmodel.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    onClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit,
    onDesignSystemClick: () -> Unit,
    viewModel: AuthViewModel = koinViewModel()
) {
    val content by viewModel.authState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = content) {
        if (content.success) {
            Log.d("SMEDIC", "LoginContent: STATE TRUE: ${content.success}")
            onClick()
        } else {
            Log.d("SMEDIC", "LoginContent: STATE FALSE: ${content.success}")
        }
    }

    Scaffold(
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                when (content.success) {
                    false -> "Not yet logged in"
                    true -> "Logged in"
                }
            )
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