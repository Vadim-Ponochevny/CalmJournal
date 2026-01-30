package com.vpnch.calmjournalapp.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun InputNameField(
    nameState: TextFieldState,
    modifier: Modifier = Modifier,
    maxLength: Int = 30
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        BasicTextField(
            state = nameState,
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .width(327.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(25.dp)
                )
                .padding(horizontal = 20.dp, vertical = 12.dp),
            inputTransformation = InputTransformation.maxLength(maxLength),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            onKeyboardAction = {
                focusManager.clearFocus()
            },
            lineLimits = TextFieldLineLimits.SingleLine
        )

        if (nameState.text.length >= maxLength) {
            Text(
                text = "Максимум символов - $maxLength",
                modifier = Modifier
                    .width(327.dp)
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}