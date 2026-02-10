package com.vpnch.calmjournalapp.features.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: TestEmotionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Заголовок
        Text(
            text = "🧪 Тест модели эмоций",
            style = MaterialTheme.typography.headlineSmall
        )

        // Поле ввода
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Введите текст для анализа...") },
            maxLines = 3
        )

        // Кнопки
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.analyzeText(text) },
                modifier = Modifier.weight(1f),
                enabled = text.isNotBlank() && !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Анализировать")
                }
            }

            Button(
                onClick = { viewModel.clear() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Очистить")
            }
        }

        // Результат
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.result?.let { result ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // Фиксированная высота для скролла
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Заголовок (не скроллируется)
                    Text(
                        text = "📊 Результат:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )

                    Divider()

                    // Содержимое (скроллируется)
                    LazyColumn(
                        modifier = Modifier.weight(1f) // Занимает всё оставшееся пространство
                    ) {
                        item {
                            Text(
                                text = result.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }

        // Ошибка
        state.error?.let { error ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "❌ Ошибка:",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Примеры для теста
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "📝 Примеры для теста:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            val examples = listOf(
                "Я счастлив сегодня!",
                "Мне грустно и одиноко",
                "Злюсь на начальника",
                "Волнуюсь перед экзаменом",
                "Люблю свою семью"
            )

            examples.forEach { example ->
                OutlinedButton(
                    onClick = {
                        text = example
                        viewModel.analyzeText(example)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = example,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}