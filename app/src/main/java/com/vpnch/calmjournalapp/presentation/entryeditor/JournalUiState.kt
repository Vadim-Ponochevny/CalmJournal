package com.vpnch.calmjournalapp.presentation.entryeditor

import com.vpnch.calmjournalapp.domain.models.JournalEntry

data class JournalUiState(
//    val entryId: Long = 0L,
    val entry: JournalEntry = JournalEntry(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val scrollToLastBlock: Boolean = false,
    val requestFocusOnLastBlock: Boolean = false,
    val error: String = "",
)