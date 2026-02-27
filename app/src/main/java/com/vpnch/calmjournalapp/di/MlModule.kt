package com.vpnch.calmjournalapp.di

import android.content.Context
import com.vpnch.calmjournalapp.data.ml.emotion.analyzer.EmotionAnalyzer
import com.vpnch.calmjournalapp.data.ml.emotion.analyzer.OnnxEmotionAnalyzer
import com.vpnch.calmjournalapp.data.ml.emotion.loader.ModelLoader
import com.vpnch.calmjournalapp.data.ml.emotion.tokenizer.BertTokenizer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MlModule {

    @Provides
    @Singleton
    fun provideBertTokenizer(@ApplicationContext context: Context): BertTokenizer {
        return BertTokenizer(context)
    }

    @Provides
    @Singleton
    fun provideModelLoader(): ModelLoader {
        return ModelLoader()
    }

    @Provides
    @Singleton
    fun provideEmotionAnalyzer(
        @ApplicationContext context: Context,
        tokenizer: BertTokenizer,
        modelLoader: ModelLoader
    ): EmotionAnalyzer {
        return OnnxEmotionAnalyzer(context, tokenizer, modelLoader)
    }
}