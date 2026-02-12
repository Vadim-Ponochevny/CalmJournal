package com.vpnch.calmjournalapp.core.data.ml.emotion.loader

import android.content.Context
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ModelLoader {

    companion object {
        private const val MODEL_PATH = "models/rubertai/model_quantized.onnx"
        const val NUM_EMOTIONS = 28
    }

    suspend fun loadModel(context: Context): Pair<OrtEnvironment, OrtSession> =
        withContext(Dispatchers.IO) {
            val ortEnvironment = OrtEnvironment.getEnvironment()
            val modelBytes = context.assets.open(MODEL_PATH).use { it.readBytes() }
            val sessionOptions = OrtSession.SessionOptions()
            val ortSession = ortEnvironment.createSession(modelBytes, sessionOptions)

            ortEnvironment to ortSession
        }
}