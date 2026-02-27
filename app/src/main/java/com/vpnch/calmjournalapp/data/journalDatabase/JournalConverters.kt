package com.vpnch.calmjournalapp.data.journalDatabase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vpnch.calmjournalapp.domain.models.JournalBlock

class JournalConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromBlocks(blocks: List<JournalBlock>): String {
        return gson.toJson(blocks)
    }

    @TypeConverter
    fun toBlocks(json: String): List<JournalBlock> {
        val type = object : TypeToken<List<JournalBlock>>() {}.type
        return gson.fromJson(json, type)
    }
}