package com.solevo.dist.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solevo.dist.R
import com.solevo.dist.ui.theme.MyApplicationTheme

@Composable
fun MainButton(
    title: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(size = 30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth(),
    ) {
        Text(
            title,
            color = Color.White,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        Image(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "Test",
            modifier = Modifier
                .size(22.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainButtonPreview() {
    MyApplicationTheme() {
        MainButton(title = "Test", onClick = { })
    }
}