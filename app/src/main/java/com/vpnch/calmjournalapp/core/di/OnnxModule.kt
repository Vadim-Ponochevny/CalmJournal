package com.vpnch.calmjournalapp.core.di

import android.content.Context
import com.vpnch.calmjournalapp.core.data.manager.OnnxModelManager
import com.vpnch.calmjournalapp.core.data.repository.EmotionAnalysisRepositoryImpl
import com.vpnch.calmjournalapp.core.domain.repository.EmotionAnalysisRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnnxModule {

    @Provides
    @Singleton
    fun provideOnnxModelManager(@ApplicationContext context: Context): OnnxModelManager {
        return OnnxModelManager(context)
    }

    @Provides
    @Singleton
    fun provideEmotionAnalysisRepository(
        onnxModelManager: OnnxModelManager
    ): EmotionAnalysisRepository {
        return EmotionAnalysisRepositoryImpl(onnxModelManager)
    }
}