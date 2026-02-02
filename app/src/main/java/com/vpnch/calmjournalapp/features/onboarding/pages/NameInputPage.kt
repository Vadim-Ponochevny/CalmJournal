package com.vpnch.calmjournalapp.features.onboarding.pages

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.features.onboarding.components.InputNameField
import com.vpnch.calmjournalapp.features.onboarding.pages.NameInputDimens.FIELD_TOP_MARGIN
import com.vpnch.calmjournalapp.features.onboarding.pages.NameInputDimens.TEXT_BLOCK_CONTENT_WIDTH
import com.vpnch.calmjournalapp.features.onboarding.pages.NameInputDimens.TITLE_SUBTITLE_GAP

private object NameInputDimens {
    const val TEXT_BLOCK_CONTENT_WIDTH = 335
    const val TITLE_SUBTITLE_GAP = 8
    const val FIELD_TOP_MARGIN = 32
}

@Composable
fun NameInputPage(
    modifier: Modifier = Modifier,
    nameState: TextFieldState,
    navigateNext: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
            detectTapGestures {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier.width(TEXT_BLOCK_CONTENT_WIDTH.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.onboarding_name_input_title),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(TITLE_SUBTITLE_GAP.dp))

                Text(
                    text = stringResource(R.string.onboarding_name_input_subtitle),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(FIELD_TOP_MARGIN.dp))

            InputNameField(
                nameState = nameState,
                navigateNext = navigateNext
            )

        }
    }
}
