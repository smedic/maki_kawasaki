package com.example.myapplication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun MainButton(
    title: String, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(size = 30.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth(),
    ) {
        Text(title, color = Color.White)
        Image(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "Test",
            modifier = Modifier
                .size(24.dp)
                .padding(horizontal = 4.dp)
        )
    }
}