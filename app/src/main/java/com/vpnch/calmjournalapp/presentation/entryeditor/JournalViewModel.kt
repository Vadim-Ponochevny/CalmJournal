package com.vpnch.calmjournalapp.presentation.entryeditor

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpnch.calmjournalapp.domain.models.JournalBlock
import com.vpnch.calmjournalapp.domain.models.JournalEntry
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import com.vpnch.calmjournalapp.domain.repository.JournalInsightRepository
import com.vpnch.calmjournalapp.domain.repository.JournalRepository
import com.vpnch.calmjournalapp.domain.result.JournalInsightResult
import com.vpnch.calmjournalapp.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    val journalInsightRepository: JournalInsightRepository,
    val journalRepository: JournalRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(JournalUiState())
    val uiState: StateFlow<JournalUiState> = _uiState

    private val entryId: Long = savedStateHandle.get<Long>(Route.JournalScreen.argEntryId) ?: -1L

    init {
        loadInitialData()
        observeStateForAutosave()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            if (entryId != -1L) {
                _uiState.update { it.copy(isLoading = true) }
                try {
                    val entry = journalRepository.getEntryById(entryId)
                    if (entry != null) {
                        _uiState.update { it.copy(entry = entry) }
                    }
                } catch (e: Exception) {
                    Log.e("JournalVM", "Error loading", e)
                } finally {
                    _uiState.update { it.copy(isLoading = false) }
                }
            } else {
                _uiState.update { it.copy(entry = it.entry.copy(id = 0L)) }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeStateForAutosave() {
        viewModelScope.launch {
            uiState
                .map { it.entry }
                .distinctUntilChanged()
                .drop(1)
                .debounce(1000L)
                .collect { entry ->
                    performSilentSave(entry)
                }
        }
    }

//    fun init(entryId: Long?) {
//        if (entryId == null) return
//
//        loadEntry(entryId)
//    }
//
//    private fun loadEntry(id: Long) {
//        viewModelScope.launch {
//            _uiState.update { it.copy(isLoading = true) }
//            try {
//                val entry = journalRepository.getEntryById(id)
//
//                _uiState.update { state ->
//                    state.copy(
//                        entry = entry ?: state.entry
//                    )
//                }
//            } catch (e: Exception) {
//                Log.e("JournalVM", "Ошибка загрузки", e)
//            } finally {
//                _uiState.update { it.copy(isLoading = false) }
//            }
//        }
//    }

    fun onEvent(event: JournalEvent) {
        when (event) {
            is JournalEvent.TitleChanged ->
                updateTitle(event.title)

            is JournalEvent.BlockContentChanged ->
                updateBlockContent(event.index, event.content)

            JournalEvent.AiHelpClicked ->
                handleAiHelpClicked()

            JournalEvent.ErrorShown ->
                _uiState.update { it.copy(errorMessage = null) }

            JournalEvent.SaveClicked -> saveEntry()

            JournalEvent.OnScrollHandled -> onScrollHandled()
        }
    }

    private fun saveEntry() {
        val entry = _uiState.value.entry
        Log.d("JournalVM", "saveEntry: ${entry.title}")

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                journalRepository.upsertEntry(entry)
                Log.d("JournalVM", "Сохранено в БД (ID=${entry.id})")

                _uiState.update { it.copy(isLoading = false) }

            } catch (e: Exception) {
                Log.e("JournalVM", "saveEntry error", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Ошибка сохранения"
                    )
                }
            }
        }
    }

    private suspend fun performSilentSave(entry: JournalEntry) {
        val savedId = journalRepository.upsertEntry(entry)

        if (entry.id == 0L) {
            _uiState.update {
                it.copy(entry = it.entry.copy(id = savedId))
            }
        }
    }

    private fun updateTitle(newTitle: String) {
        _uiState.update { state ->
            state.copy(entry = state.entry.copy(title = newTitle))
        }
    }

    private fun updateBlockContent(index: Int, newContent: String) {
        _uiState.update { state ->
            val current = state.entry
            val newBlocks = current.blocks.toMutableList().apply {
                if (index < size && this[index] is JournalBlock.User) {
                    val userBlock = this[index] as JournalBlock.User
                    this[index] = userBlock.copy(content = newContent)
                }
            }
            state.copy(entry = current.copy(blocks = newBlocks))
        }
    }

    private fun handleAiHelpClicked() {
        val entry = _uiState.value.entry
        val prompt = entry.getLastUserText()

        if (prompt.isBlank()) {
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = "",
                    scrollToLastBlock = false,
                    requestFocusOnLastBlock = false
                )
            }

            try {
                Log.d("JournalVM", "Отправляем в AI: $prompt")
                val response = journalInsightRepository.analyzeJournalEntry(entry.asChatHistory())

                if (response is JournalInsightResult.Success) {

                    val newBlocks = entry.blocks.toMutableList().apply {
                        add(JournalBlock.Ai(text = response.data))
                        add(JournalBlock.User(content = ""))
                    }

                    val updatedEntry = entry.copy(blocks = newBlocks)

                    _uiState.update { state ->
                        state.copy(
                            entry = updatedEntry,
                            isLoading = false,
                            scrollToLastBlock = true,
                            requestFocusOnLastBlock = true
                        )
                    }

                    Log.d("JournalVM", "AI добавил блок: ${response.data.take(50)}...")

                    // saveEntry()

                } else if (response is JournalInsightResult.Error) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = response.message
                        )
                    }
                }

            } catch (e: Exception) {
                Log.e("JournalVM", "AI запрос упал", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Ошибка сети"
                    )
                }
            }
        }
    }

    private fun onScrollHandled() {
        _uiState.update { it.copy(scrollToLastBlock = false, requestFocusOnLastBlock = false) }
    }
}
