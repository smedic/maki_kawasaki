package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.composables.OtpTextField
import com.example.myapplication.views.MainButton
import com.simon.xmaterialccp.component.MaterialCountryCodePicker
import com.simon.xmaterialccp.data.CountryData
import com.simon.xmaterialccp.data.ccpDefaultColors
import com.togitech.ccp.component.TogiCountryCodePicker
import com.example.myapplication.composables.OtpTextField

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
            contentAlignment = Alignment.Center
        ) {

            val phoneNumber = rememberSaveable { mutableStateOf("") }
            val fullPhoneNumber = rememberSaveable { mutableStateOf("") }
            val onlyPhoneNumber = rememberSaveable { mutableStateOf("") }
            var otpValue = rememberSaveable { mutableStateOf("") }

            Column {

                OtpTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp),
                    otpText = otpValue.value,
                    otpCount = 4,
                    asPin = true,
                    onOtpTextChange = { value, otpInputFilled ->
                        otpValue.value = value
                    }
                )

                MainButton(
                    title = "Test",
                    modifier = Modifier.padding(horizontal = 30.dp),
                    onClick = {},
                )
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
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
                MaterialCountryCodePicker(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    text = phoneNumber.value,
                    pickedCountry = {},
                    onValueChange = { phoneNumber.value = it },
                    defaultCountry = CountryData(cCodes = "+381"),
                    colors = ccpDefaultColors()
                )
            }
        }
    }
}