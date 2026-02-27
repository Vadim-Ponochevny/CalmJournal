package com.vpnch.calmjournalapp.presentation.entryeditor

sealed class JournalEvent {
    data class TitleChanged(val title: String) : JournalEvent()
    data class BlockContentChanged(val index: Int, val content: String) : JournalEvent()
    data object AiHelpClicked : JournalEvent()
    data object ErrorShown : JournalEvent()
    data object SaveClicked : JournalEvent()
    data object OnScrollHandled : JournalEvent()
}