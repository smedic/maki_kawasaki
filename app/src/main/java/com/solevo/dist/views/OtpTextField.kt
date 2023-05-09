package com.solevo.dist.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.solevo.dist.ui.theme.Black
import com.solevo.dist.ui.theme.Blue500
import com.solevo.dist.ui.theme.LightGrey

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    asPin: Boolean = false,
    onOtpTextChange: (String, Boolean) -> Unit,
) {

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()

        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
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
    modifier: Modifier = Modifier,
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
    modifier: Modifier = Modifier,
) {
    val char = when {
        index >= text.length -> ""
        else -> text[index].toString()
    }

    Canvas(
        modifier = modifier
            .size(size = 40.dp)
    ) {
        drawCircle(
            color = if (char.isEmpty()) LightGrey else Blue500,
            radius = 6.dp.toPx(),
        )
    }
}