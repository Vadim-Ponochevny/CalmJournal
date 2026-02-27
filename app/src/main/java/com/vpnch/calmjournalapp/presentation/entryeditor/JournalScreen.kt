package com.vpnch.calmjournalapp.presentation.entryeditor

import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.domain.models.JournalBlock

@Composable
fun JournalScreen(
    viewModel: JournalViewModel,
    navController: NavController
) {
    val state by viewModel.uiState.collectAsState()
    val entry = state.entry

    val listState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }

    val noteBackgroundAnimatable = remember { Animatable(Color.White) }

    LaunchedEffect(
        entry.blocks.size,
        state.scrollToLastBlock,
        state.requestFocusOnLastBlock
    ) {
        if (state.scrollToLastBlock && entry.blocks.isNotEmpty()) {
            listState.animateScrollToItem(entry.blocks.size - 1)
            viewModel.onEvent(JournalEvent.OnScrollHandled)
        }
        if (state.requestFocusOnLastBlock) {
            focusRequester.requestFocus()
        }
    }

    Scaffold(containerColor = Color.Transparent) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(padding)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 80.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    BasicTextField(
                        value = entry.title,
                        onValueChange = { viewModel.onEvent(JournalEvent.TitleChanged(it)) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        textStyle = MaterialTheme.typography.titleMedium,
                        decorationBox = { inner ->
                            if (entry.title.isEmpty()) {
                                Text("Заголовок...", color = Color.Gray)
                            }
                            inner()
                        }
                    )
                }

                itemsIndexed(entry.blocks) { index, block ->
                    when (block) {
                        is JournalBlock.User -> UserBlockItem(
                            content = block.content,
                            onContentChange = { viewModel.onEvent(JournalEvent.BlockContentChanged(index, it)) },
                            modifier = if (index == entry.blocks.lastIndex)
                                Modifier.focusRequester(focusRequester)
                            else Modifier
                        )

                        is JournalBlock.Ai -> AiBlockItem(text = block.text)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.TopCenter)
                    .blur(radius = 15.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                noteBackgroundAnimatable.value.copy(alpha = 0.8f),
                                noteBackgroundAnimatable.value.copy(alpha = 0.4f),
                                Color.Transparent
                            )
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    onClick = {
                        navController.popBackStack();
                    },
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    tonalElevation = 0.dp,
                    shadowElevation = 2.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.btn_back),
                            contentDescription = stringResource(R.string.back_button_description),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Surface(
                    onClick = { viewModel.onEvent(JournalEvent.AiHelpClicked) },
                    modifier = Modifier.size(width = 108.dp, height = 48.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface,
                    shadowElevation = 4.dp
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Разбор",
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserBlockItem(
    content: String,
    onContentChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {

        Spacer(Modifier.height(8.dp))
        BasicTextField(
            value = content,
            onValueChange = onContentChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            textStyle = MaterialTheme.typography.bodySmall,
            decorationBox = { inner ->
                if (content.isEmpty()) {
                    Text("Текст", color = Color.Gray)
                }
                inner()
            }
        )
    }
}

@Composable
fun AiBlockItem(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Surface(
            color = Color(0xFFE1F0FF),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
