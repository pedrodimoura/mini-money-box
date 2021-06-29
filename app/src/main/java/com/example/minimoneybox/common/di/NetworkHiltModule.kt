package com.example.minimoneybox.common.di

import android.util.Log
import com.example.minimoneybox.BuildConfig
import com.example.minimoneybox.common.data.networking.HttpClient
import com.example.minimoneybox.common.data.networking.retrofit.RetrofitClientImpl
import com.example.minimoneybox.common.domain.SessionRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiKeyInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LoggingInterceptor

const val OK_HTTP_LOGGING_TAG = "OK_HTTP_LOGGING"

@Module
@InstallIn(SingletonComponent::class)
object NetworkHiltModule {

    private const val APP_ID_KEY = "AppId"
    private const val APP_VERSION_KEY = "appVersion"
    private const val API_VERSION_KEY = "apiVersion"
    private const val CONTENT_TYPE_KEY = "Content-Type"
    private const val CONTENT_TYPE = "application/json"
    private const val AUTHORIZATION_KEY = "Authorization"
    private const val BEARER = "Bearer"

    @ApiKeyInterceptor
    @Provides
    fun providesApiKeyInterceptor(
        sessionRepository: SessionRepository,
    ): Interceptor {
        return Interceptor { chain ->

            sessionRepository.refresh()

            val request = chain.request()

            val newRequest = request.newBuilder()
                .addHeader(APP_ID_KEY, BuildConfig.APP_ID)
                .addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE)
                .addHeader(APP_VERSION_KEY, BuildConfig.VERSION_NAME)
                .addHeader(API_VERSION_KEY, BuildConfig.API_VERSION)
                .build()

            chain.proceed(newRequest)
        }
    }

    @Provides
    fun providesAuthenticator(
        sessionRepository: SessionRepository,
    ): Authenticator = Authenticator { _, response ->
        if (response.request.header(AUTHORIZATION_KEY) != null) {
            null
        } else {
            val userCredential = sessionRepository.get()
            response.request.newBuilder()
                .addHeader(AUTHORIZATION_KEY, "$BEARER ${userCredential.token}")
                .build()
        }
    }

    @LoggingInterceptor
    @Provides
    fun providesLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor {
            Log.i(OK_HTTP_LOGGING_TAG, it)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesGsonConverterFactory(
        gson: Gson,
    ) = GsonConverterFactory.create(gson)

    @Provides
    fun providesOkHttpClient(
        @ApiKeyInterceptor apiKeyInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor,
        authenticator: Authenticator,
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .authenticator(authenticator)
            .addInterceptor(apiKeyInterceptor)

        if (BuildConfig.DEBUG)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)

        return okHttpClientBuilder.build()
    }

    @Provides
    fun providesRetrofitHttpClient(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): HttpClient.RetrofitClient =
        RetrofitClientImpl(BuildConfig.BASE_URL, okHttpClient, gsonConverterFactory)
}
