package com.vpnch.calmjournalapp.data.journal.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vpnch.calmjournalapp.domain.journal.model.JournalBlock
import com.vpnch.calmjournalapp.domain.journal.model.JournalEntry
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Entity(tableName = "journal_entries")
data class JournalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: Long,
    val blocksJson: String
)

object JsonConfig {
    val journalJson = Json {
        ignoreUnknownKeys = true
        classDiscriminator = "blockType"
        encodeDefaults = true
        serializersModule = SerializersModule {
            polymorphic(JournalBlock::class) {
                subclass(JournalBlock.User::class)
                subclass(JournalBlock.Ai::class)
            }
        }
    }
}

fun JournalEntity.toDomain(): JournalEntry {
    return JournalEntry(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        blocks = try {
            JsonConfig.journalJson.decodeFromString(blocksJson)
        } catch (e: Exception) {
            try {
                val old = Json.decodeFromString<List<JournalBlock.User>>(blocksJson)
                old
            } catch (e: Exception) {
                emptyList()
            }
        }
    )
}