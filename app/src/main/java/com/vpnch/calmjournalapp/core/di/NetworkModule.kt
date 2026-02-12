package com.vpnch.calmjournalapp.core.di

import android.util.Log
import com.vpnch.calmjournalapp.core.data.gigachat.network.GigaChatApi
import com.vpnch.calmjournalapp.core.data.gigachat.network.GigaOAuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Named
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object GigaChatNetworkModule {

    val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("GigaChat_HTTP", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY  // request + response + headers
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .hostnameVerifier { _, _ -> true }
            .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
            .build()
    }

    @Provides
    @Singleton
    @Named("oauthRetrofit")
    fun provideOauthRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://ngw.devices.sberbank.ru:9443/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @Named("apiRetrofit")
    fun provideApiRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://gigachat.devices.sberbank.ru/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideGigaOAuthApi(
        @Named("oauthRetrofit") retrofit: Retrofit
    ): GigaOAuthApi = retrofit.create(GigaOAuthApi::class.java)

    @Provides
    @Singleton
    fun provideGigaChatApi(
        @Named("apiRetrofit") retrofit: Retrofit
    ): GigaChatApi = retrofit.create(GigaChatApi::class.java)
}
