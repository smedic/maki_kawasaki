package com.example.myapplication.screens

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.Blue500
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.views.MainButton
import com.example.myapplication.views.OtpTextField
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.TimeUnit

@Composable
fun OtpCodePage(
    onCodeConfirmed: () -> Unit,
    viewModel: AuthViewModel = koinViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val otpValueState = rememberSaveable { mutableStateOf("") }
    val verificationIdState = rememberSaveable { mutableStateOf(viewModel.verificationId) }
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.otp_code),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Enter Code",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(4.dp))
            OtpTextField(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp),
                otpText = otpValueState.value,
                otpCount = 6,
                asPin = false,
                onOtpTextChange = { value, _ -> otpValueState.value = value }
            )
            Spacer(modifier = Modifier.height(12.dp))
            MainButton(
                title = "Next",
                modifier = Modifier.padding(horizontal = 30.dp),
                onClick = {
                    val credential = PhoneAuthProvider.getCredential(
                        verificationIdState.value, otpValueState.value
                    )
                    signInWithPhoneAuthCredential(
                        credential,
                        auth,
                        context as Activity,
                        onSuccess = { onCodeConfirmed() },
                        onFailure = {
                            Log.d("OtpCodePage", "OtpCodePage: failure: $it")
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ClickableText(
                AnnotatedString("Resend Code"),
                style = TextStyle(
                    color = Blue500,
                    fontSize = 16.sp,
                ),
                onClick = {
                    forceSendVerificationCode(
                        viewModel.phoneNumber,
                        auth,
                        context as Activity,
                        viewModel.forceResendingToken,
                        callbacks,
                    )
                }
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

        override fun onCodeSent(verificationId: String, p1: ForceResendingToken) {
            super.onCodeSent(verificationId, p1)
            verificationIdState.value = verificationId
        }
    }
}

private fun signInWithPhoneAuthCredential(
    credential: PhoneAuthCredential,
    auth: FirebaseAuth,
    activity: Activity,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit,
) {
    // on below line signing with credentials.
    auth.signInWithCredential(credential)
        .addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    val error = (task.exception as FirebaseAuthInvalidCredentialsException).message
                    error?.let { onFailure(error) }
                }
            }
        }
}

private fun forceSendVerificationCode(
    number: String,
    auth: FirebaseAuth,
    activity: Activity,
    forceResendingToken: ForceResendingToken,
    callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
) {
    PhoneAuthProvider.verifyPhoneNumber(
        PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .setForceResendingToken(forceResendingToken)
            .build()
    )
}