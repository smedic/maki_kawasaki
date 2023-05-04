package com.example.myapplication.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.Black
import com.example.myapplication.ui.theme.Blue500
import com.example.myapplication.ui.theme.LightGrey

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    asPin: Boolean = false,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally)) {
                repeat(otpCount) { index ->
                    if (asPin) {
                        PinView(
                            index = index,
                            text = otpText,
                        )
                    } else {
                        CharView(
                            index = index,
                            text = otpText,
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    val char = when {
        index >= text.length -> ""
        else -> text[index].toString()
    }

    Text(
        modifier = modifier
            .widthIn(min = 40.dp, max = 45.dp)
            .height(56.dp)
            .background(shape = RoundedCornerShape(8.dp), color = LightGrey)
            .padding(8.dp),
        text = char,
        style = MaterialTheme.typography.h4,
        color = Black,
        textAlign = TextAlign.Center,
    )
}


@Composable
private fun PinView(
    index: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    val char = when {
        index >= text.length -> ""
        else -> text[index].toString()
    }

    Canvas(modifier = Modifier
            .size(size = 40.dp)) {
        drawCircle(
            color = if (char.isEmpty())  LightGrey else Blue500,
            radius = 6.dp.toPx(),
        )
    }
}