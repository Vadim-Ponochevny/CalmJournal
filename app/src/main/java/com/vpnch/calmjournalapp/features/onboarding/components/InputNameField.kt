package com.vpnch.calmjournalapp.features.onboarding.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.features.onboarding.components.InputNameFieldDimens.COUNTER_HORIZONTAL_PADDING
import com.vpnch.calmjournalapp.features.onboarding.components.InputNameFieldDimens.COUNTER_VERTICAL_PADDING
import com.vpnch.calmjournalapp.features.onboarding.components.InputNameFieldDimens.FIELD_CORNER_RADIUS
import com.vpnch.calmjournalapp.features.onboarding.components.InputNameFieldDimens.FIELD_WIDTH
import com.vpnch.calmjournalapp.features.onboarding.components.InputNameFieldDimens.HORIZONTAL_PADDING
import com.vpnch.calmjournalapp.features.onboarding.components.InputNameFieldDimens.MAX_LENGTH_DEFAULT
import com.vpnch.calmjournalapp.features.onboarding.components.InputNameFieldDimens.VERTICAL_PADDING

private object InputNameFieldDimens {
    const val FIELD_WIDTH = 327
    const val HORIZONTAL_PADDING = 20
    const val VERTICAL_PADDING = 12
    const val COUNTER_VERTICAL_PADDING = 4
    const val COUNTER_HORIZONTAL_PADDING = 20
    const val MAX_LENGTH_DEFAULT = 30
    const val FIELD_CORNER_RADIUS = 25
}

@Composable
fun InputNameField(
    nameState: TextFieldState,
    modifier: Modifier = Modifier,
    navigateNext: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        BasicTextField(
            state = nameState,
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .width(FIELD_WIDTH.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(FIELD_CORNER_RADIUS.dp)
                )
                .padding(
                    horizontal = HORIZONTAL_PADDING.dp,
                    vertical = VERTICAL_PADDING.dp
                ),
            inputTransformation = InputTransformation.maxLength(MAX_LENGTH_DEFAULT),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            onKeyboardAction = {
                focusManager.clearFocus()
                navigateNext()
            },
            lineLimits = TextFieldLineLimits.SingleLine
        )

        if (nameState.text.length >= MAX_LENGTH_DEFAULT) {
            Text(
                text = stringResource(
                    R.string.onboarding_name_max_length,
                    MAX_LENGTH_DEFAULT
                ),
                modifier = Modifier
                    .width(FIELD_WIDTH.dp)
                    .padding(
                        horizontal = COUNTER_HORIZONTAL_PADDING.dp,
                        vertical = COUNTER_VERTICAL_PADDING.dp
                    ),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}