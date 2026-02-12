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
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.core.designsystem.Dimens.cornerRadiusLarge
import com.vpnch.calmjournalapp.core.designsystem.Dimens.fieldMaxWidth
import com.vpnch.calmjournalapp.core.designsystem.Dimens.spacingMedium
import com.vpnch.calmjournalapp.core.designsystem.Dimens.spacingSmallRegular
import com.vpnch.calmjournalapp.core.designsystem.Dimens.spacingTiny

private const val MAX_LENGTH_DEFAULT = 30

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
                .width(fieldMaxWidth)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(cornerRadiusLarge)
                )
                .padding(
                    horizontal = spacingMedium,
                    vertical = spacingSmallRegular
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
                    .width(fieldMaxWidth)
                    .padding(
                        horizontal = spacingMedium,
                        vertical = spacingTiny
                    ),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}