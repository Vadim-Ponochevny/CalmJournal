package com.vpnch.calmjournalapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.vpnch.calmjournalapp.domain.user.model.User
import com.vpnch.calmjournalapp.domain.journal.repository.JournalRepository
import com.vpnch.calmjournalapp.domain.user.repository.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val userPreferences: UserRepository,
    private val journalRepository: JournalRepository
) : ViewModel() {

    val userData: StateFlow<User?> = userPreferences.userDataFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var observeEntriesJob: Job? = null

    init {
        observeJournalEntries()
    }

    fun observeJournalEntries() {
        observeEntriesJob?.cancel()
        observeEntriesJob = journalRepository.getAllEntries()
            .onStart {
                _uiState.update { it.copy(isLoading = true) }
            }
            .onEach { entries ->
                _uiState.update {
                    it.copy(
                        journalEntries = entries,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.NavigateToJournal -> _uiState.update { it.copy(navigateToJournal = true) }
            is HomeEvent.DeleteRecord -> deleteRecord(event.id)
            HomeEvent.NavigateBack -> _uiState.update { it.copy(navigateToJournal = false) }
            HomeEvent.ErrorShown -> _uiState.update { it.copy(error = null) }
        }
    }

    private fun deleteRecord(id: Long) {
        viewModelScope.launch {
            try {
                journalRepository.deleteEntry(id)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Ошибка удаления",
                        isLoading = false
                    )
                }
            }
        }
    }

}
