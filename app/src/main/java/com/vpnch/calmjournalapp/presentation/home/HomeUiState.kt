package com.vpnch.calmjournalapp.presentation.home

import com.vpnch.calmjournalapp.domain.journal.model.JournalEntry

data class HomeUiState(
    val journalEntries: List<JournalEntry> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val navigateToJournal: Boolean = false
)