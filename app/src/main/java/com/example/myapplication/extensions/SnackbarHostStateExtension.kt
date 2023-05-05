package com.example.myapplication.extensions

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import com.example.myapplication.R
import com.example.myapplication.views.compose.FetchingError

// https://issuetracker.google.com/issues/279780923
suspend fun SnackbarHostState.showMessage(
    error: FetchingError?,
    context: Context
) {
    val message =
        if (error?.message?.isNotBlank() == true) error.message else context.getString(
            R.string.there_was_a_problem
        )
    showSnackbar(message = message)
}
