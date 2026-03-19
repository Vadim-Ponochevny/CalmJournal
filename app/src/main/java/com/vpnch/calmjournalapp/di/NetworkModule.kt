package com.vpnch.calmjournalapp.di

import android.content.Context
import android.util.Log
import com.vpnch.calmjournalapp.R
import com.vpnch.calmjournalapp.data.insightentry.gigachat.network.GigaChatApi
import com.vpnch.calmjournalapp.data.insightentry.gigachat.network.GigaOAuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Named
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.KeyStore
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager

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
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return provideGigaChatOkHttp(context)
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

fun provideGigaChatOkHttp(context: Context): OkHttpClient {
    val cf = CertificateFactory.getInstance("X.509")
    val ca: X509Certificate = context.resources
        .openRawResource(R.raw.russian_trusted_root_ca)
        .use { input -> cf.generateCertificate(input) as X509Certificate }

    val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
        load(null, null)
        setCertificateEntry("russian_root", ca)
    }

    val tmf = TrustManagerFactory.getInstance(
        TrustManagerFactory.getDefaultAlgorithm()
    ).apply {
        init(keyStore)
    }
    val trustManager = tmf.trustManagers
        .filterIsInstance<X509TrustManager>()
        .first()

    val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, arrayOf<TrustManager>(trustManager), null)
    }

    return OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustManager)
        .addInterceptor(
            HttpLoggingInterceptor { msg -> Log.d("GigaChat_HTTP", msg) }
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .hostnameVerifier { hostname, session ->
            HttpsURLConnection.getDefaultHostnameVerifier()
                .verify(hostname, session)
        }
        .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
        .build()
}
