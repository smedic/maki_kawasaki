package com.solevo.dist.extensions

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import com.solevo.dist.R
import com.solevo.dist.views.compose.FetchingError

// https://issuetracker.google.com/issues/279780923
suspend fun SnackbarHostState.showMessage(
    error: FetchingError?,
    context: Context,
) {
    val message =
        if (error?.message?.isNotBlank() == true) error.message else context.getString(
            R.string.there_was_a_problem
        )
    showSnackbar(message = message)
}
