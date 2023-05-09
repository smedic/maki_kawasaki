package com.solevo.dist.views.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.solevo.dist.views.MainButton

@Composable
fun ErrorView(error: FetchingError?, onRetryPress: (() -> Unit)) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = painterResource(android.R.drawable.stat_notify_error),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
            contentDescription = null,
            modifier = Modifier.size(width = 76.dp, height = 76.dp)
        )
        Text(
            text = if (error?.message?.isNotBlank() == true) error.message else "Error...",
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        MainButton(
            title = "Retry",
            onClick = { onRetryPress.invoke() },
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
