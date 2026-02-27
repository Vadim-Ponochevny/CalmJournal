package com.vpnch.calmjournalapp.presentation.home

sealed class HomeEvent {
//    object RefreshRecords : HomeEvent()
    object NavigateToJournal : HomeEvent()
    data class DeleteRecord(val id: Long) : HomeEvent()
    object NavigateBack : HomeEvent()
    object ErrorShown : HomeEvent()
}