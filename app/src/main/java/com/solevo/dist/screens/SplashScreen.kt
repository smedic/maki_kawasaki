package com.solevo.dist.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solevo.dist.R
import com.solevo.dist.viewmodel.SplashViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    onLoadingEnd: (String) -> Unit,
    viewModel: SplashViewModel = koinViewModel(),
) {
    val isLoading by viewModel.isLoading

    LaunchedEffect(key1 = isLoading) {
        onLoadingEnd(viewModel.startDestination.value)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(180.dp),
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = "Logo"
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(id = R.string.splash_screen_title),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(onLoadingEnd = {})
}