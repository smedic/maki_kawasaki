package com.solevo.dist.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.solevo.dist.views.MainButton
import com.solevo.dist.views.OtpTextField
import com.togitech.ccp.component.TogiCountryCodePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesignSystemScreen(onBackClicked: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Design system") },
                navigationIcon = {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            val otpValue = rememberSaveable { mutableStateOf("") }

            Column {
                OtpTextField(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp),
                    otpText = otpValue.value,
                    otpCount = 4,
                    asPin = true,
                    onOtpTextChange = { value, otpInputFilled ->
                        otpValue.value = value
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                MainButton(
                    title = "Test",
                    modifier = Modifier.padding(horizontal = 30.dp),
                    onClick = {},
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier.padding(horizontal = 4.dp)) {
                    TogiCountryCodePicker(
                        modifier = Modifier.background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        ),
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = Color.Blue,
                        text = "123456",
                        onValueChange = {},
                        bottomStyle = false, //  if true the text-field is below the country code selector at the top.
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }
        }
    }
}