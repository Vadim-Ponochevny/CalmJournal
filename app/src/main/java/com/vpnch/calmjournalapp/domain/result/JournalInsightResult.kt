package com.vpnch.calmjournalapp.domain.result

sealed interface JournalInsightResult<out T> {
    data class Success<T>(val data: T) : JournalInsightResult<T>
    data class Error(
        val message: String,
        val cause: Throwable? = null
    ) : JournalInsightResult<Nothing>
}