package com.example.myapplication.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.views.MainButton
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.togitech.ccp.component.TogiCountryCodePicker
import com.togitech.ccp.component.getFullPhoneNumber
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.TimeUnit

@Composable
fun LoginPage(
    onCodeSent: () -> Unit,
    viewModel: AuthViewModel = koinViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val auth: FirebaseAuth = FirebaseAuth.getInstance();
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.phone_number),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Enter Phone Number",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                TogiCountryCodePicker(
                    modifier = Modifier.background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    ),
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color.Blue,
                    text = "64555555",
                    onValueChange = {},
                    bottomStyle = false, //  if true the text-field is below the country code selector at the top.
                    shape = RoundedCornerShape(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            MainButton(
                title = "Send Code",
                modifier = Modifier.padding(horizontal = 30.dp),
                onClick = {
                    sendVerificationCode(
                        getFullPhoneNumber(),
                        auth,
                        context as Activity,
                        callbacks
                    )
                },
            )
        }
    }

    callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Toast.makeText(context, "Verification successful..", Toast.LENGTH_SHORT).show()
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(context, "Verification failed..", Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            super.onCodeSent(verificationId, token)
            viewModel.phoneNumber = getFullPhoneNumber()
            viewModel.verificationId = verificationId
            viewModel.forceResendingToken = token
            onCodeSent()
        }
    }
}

private fun sendVerificationCode(
    number: String,
    auth: FirebaseAuth,
    activity: Activity,
    callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
) {
    PhoneAuthProvider.verifyPhoneNumber(
        PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
    )
}