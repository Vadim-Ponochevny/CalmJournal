package com.vpnch.calmjournalapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.vpnch.calmjournalapp.core.data.gigachat.repository.JournalInsightRepositoryImpl
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class DiaryAnalysisViewModel @Inject constructor(
    private val gigaRepo: JournalInsightRepositoryImpl
) : ViewModel() {

    private val _userText = MutableStateFlow("")
    val userText: StateFlow<String> = _userText.asStateFlow()

    private val _analysisResult = MutableStateFlow<String?>(null)
    val analysisResult: StateFlow<String?> = _analysisResult.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun onUserTextChanged(text: String) {
        _userText.value = text
    }

    fun clearUserText() {
        _userText.value = ""
        _analysisResult.value = null  // очищаем и ответ
    }

    fun analyzeDiaryText() {
        viewModelScope.launch {
            _isLoading.value = true
            _analysisResult.value = null  // сбрасываем предыдущий ответ

            try {
                val result = gigaRepo.analyzeJournalEntry(_userText.value)
//                _analysisResult.value = result
            } catch (e: Exception) {
                _analysisResult.value = "Не удалось получить ответ. Проверьте подключение к интернету."
            } finally {
                _isLoading.value = false
            }
        }
    }
}
