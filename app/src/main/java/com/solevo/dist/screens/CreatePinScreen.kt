package com.solevo.dist.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solevo.dist.R
import com.solevo.dist.viewmodel.AuthViewModel
import com.solevo.dist.views.MainButton
import com.solevo.dist.views.OtpTextField
import com.solevo.dist.views.compose.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreatePinScreen(
    onFinish: () -> Unit,
    viewModel: AuthViewModel = koinViewModel(),
) {
    val pinState = viewModel.pinState.collectAsState()
    val otpValueState = rememberSaveable { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val pinCount = 4

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->

        Box() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pin_enter),
                    modifier = Modifier.size(116.dp),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(id = R.string.create_pin),
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.height(25.dp))
                OtpTextField(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp),
                    otpText = otpValueState.value,
                    otpCount = pinCount,
                    asPin = false,
                    onOtpTextChange = { value, _ ->
                        otpValueState.value = value
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                MainButton(
                    title = stringResource(id = R.string.finish),
                    enabled = otpValueState.value.count() == pinCount,
                    modifier = Modifier.padding(horizontal = 25.dp),
                    onClick = {
                        viewModel.createPINCode(otpValueState.value)
                    }
                )
                Spacer(modifier = Modifier.height(50.dp))
            }

            pinState.value?.let {
                when (it) {
                    is UiState.Error -> {
                        Toast.makeText(
                            LocalContext.current,
                            "Unable to create pin",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is UiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is UiState.Success -> {
                        LaunchedEffect(Unit) {
                            onFinish()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CreatePinScreenPreview() {
    CreatePinScreen(onFinish = {})
}
