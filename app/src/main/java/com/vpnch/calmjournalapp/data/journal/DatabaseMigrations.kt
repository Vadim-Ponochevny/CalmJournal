package com.vpnch.calmjournalapp.data.journal

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Room автоматически обработает изменение @PrimaryKey(autoGenerate = true)
        // Ничего дополнительно делать НЕ нужно!

        Log.d("Migration", "✅ Миграция 1→2 выполнена")
    }
}

// 🔥 Миграция 2→3 (если добавишь новые поля в будущем)
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Пример: добавляем новое поле createdAt
        database.execSQL("""
            ALTER TABLE journal_entries 
            ADD COLUMN createdAt INTEGER NOT NULL DEFAULT ${System.currentTimeMillis()}
        """.trimIndent())
    }
}