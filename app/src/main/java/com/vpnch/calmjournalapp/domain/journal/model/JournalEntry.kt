package com.vpnch.calmjournalapp.domain.journal.model

import com.vpnch.calmjournalapp.data.insightentry.gigachat.network.dto.ChatMessage
import com.vpnch.calmjournalapp.data.journal.model.JournalEntity
import com.vpnch.calmjournalapp.data.journal.model.JsonConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString

data class JournalEntry(
    val id: Long = 0L,
    val title: String = "",
    val content: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val blocks: List<JournalBlock> = listOf(
        JournalBlock.User(content = "")
    )
) {
    fun getLastUserText(): String =
        blocks.filterIsInstance<JournalBlock.User>()
            .lastOrNull()?.content ?: ""

    fun asChatHistory(): List<ChatMessage> = blocks.map { block ->
        when (block) {
            is JournalBlock.User ->
                ChatMessage(role = "user", content = block.content)
            is JournalBlock.Ai ->
                ChatMessage(role = "assistant", content = block.text)
        }
    }
}

@Serializable
sealed interface JournalBlock {

    @Serializable
    @SerialName("user")
    data class User(val content: String) : JournalBlock

    @Serializable
    @SerialName("ai")
    data class Ai(val text: String) : JournalBlock
}

fun JournalEntry.toEntity(): JournalEntity {
    return JournalEntity(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        blocksJson = JsonConfig.journalJson.encodeToString<List<JournalBlock>>(blocks)
    )
}
