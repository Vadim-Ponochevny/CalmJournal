package com.vpnch.calmjournalapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpnch.calmjournalapp.core.domain.models.EmotionAnalysisResult
import com.vpnch.calmjournalapp.core.domain.models.EmotionScore
import com.vpnch.calmjournalapp.core.domain.usecases.analyze.AnalyzeJournalEntryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.util.Log

@HiltViewModel
class TestEmotionViewModel @Inject constructor(
    private val analyzeEmotionUseCase: AnalyzeJournalEntryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TestEmotionState())
    val state = _state.asStateFlow()

    fun analyzeText(text: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                val result = analyzeEmotionUseCase(text)
                _state.value = _state.value.copy(
                    isLoading = false,
                    result = result,
                    error = null
                )
                Log.i("result", "$result")
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка",
                    result = null
                )
            }
        }
    }

    fun clear() {
        _state.value = TestEmotionState()
    }
}

data class TestEmotionState(
    val isLoading: Boolean = false,
    val result: Any? = null,
    val error: String? = null
)