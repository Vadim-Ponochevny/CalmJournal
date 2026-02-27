package com.vpnch.calmjournalapp.presentation.onboarding.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object BitmapTempSaver {
    private const val AVATAR_PREFIX = "avatar_"

    fun saveBitmapToTempFile(context: Context, bitmap: Bitmap?): Uri? {
        if (bitmap == null) return null

        val fileName = "$AVATAR_PREFIX${System.currentTimeMillis()}.jpg"
        val file = File(context.cacheDir, fileName)

        return try {
            FileOutputStream(file).use { out ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                    throw IOException("Failed to compress bitmap")
                }
            }
            file.toUri()
        } catch (e: IOException) {
            Log.e("BitmapTempSaver", "Error saving bitmap: $e")
            null
        }
    }

    fun clearOldAvatars(context: Context) {
        try {
            val cacheFiles = context.cacheDir.listFiles()
            cacheFiles?.forEach { file ->
                if (file.name.startsWith(AVATAR_PREFIX)) {
                    file.delete()
                }
            }
        } catch (e: Exception) {
            Log.e("BitmapTempSaver", "Error clearing cache: $e")
        }
    }
}