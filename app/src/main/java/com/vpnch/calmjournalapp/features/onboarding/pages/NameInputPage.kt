package com.vpnch.calmjournalapp.features.onboarding.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NameInputPage(
    onNext: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    var name by viewModel.userName.collectAsState()
    var nameError by viewModel.nameError.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.name_input_title),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = stringResource(R.string.name_input_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text(stringResource(R.string.enter_name)) },
            modifier = Modifier.width(300.dp),
            isError = nameError != null,
            supportingText = {
                nameError?.let { Text(it) }
            },
            singleLine = true,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(32.dp))

        NextButton(
            pagerState = rememberPagerState(),
            onNext = {
                if (viewModel.validateName()) {
                    onNext()
                }
            }
        )
    }
}
