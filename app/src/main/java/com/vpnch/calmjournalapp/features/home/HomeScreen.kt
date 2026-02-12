package com.vpnch.calmjournalapp.features.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: DiaryAnalysisViewModel = hiltViewModel()
) {
    val userText by viewModel.userText.collectAsState()
    val analysisResult by viewModel.analysisResult.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            value = userText,
            onValueChange = viewModel::onUserTextChanged,
            label = { Text("Что сегодня произошло?") },
            placeholder = { Text("Расскажите о своих мыслях и чувствах...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),  // многострочное поле
            maxLines = 4,
            minLines = 3,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
            ),
            trailingIcon = {
                if (userText.isNotBlank()) {
                    TextButton(
                        onClick = { viewModel.clearUserText() }
                    ) { Text("Очистить") }
                }
            }
        )

        // 2. КНОПКА АНАЛИЗА
        Button(
            onClick = { viewModel.analyzeDiaryText() },
            modifier = Modifier.fillMaxWidth(),
            enabled = userText.isNotBlank() && !isLoading
        ) {
            if (isLoading) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Анализирую...")
                }
            } else {
                Text("Получить мягкий совет")
            }
        }

        // 3. ПОЛЕ С ОТВЕТОМ ИИ
        if (analysisResult != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "🤗 Совет от ИИ-помощника",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = analysisResult!!,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 24.sp
                    )
                }
            }
        }
    }
}
