package com.example.minimoneybox.common.network.di

import android.util.Log
import com.example.minimoneybox.BuildConfig
import com.example.minimoneybox.common.di.ApiKeyInterceptor
import com.example.minimoneybox.common.di.LoggingInterceptor
import com.example.minimoneybox.common.di.OK_HTTP_LOGGING_TAG
import com.example.minimoneybox.common.networking.HttpClient
import com.example.minimoneybox.common.networking.retrofit.RetrofitClientImpl
import com.example.minimoneybox.common.testUrl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkHiltModuleTest {

    @ApiKeyInterceptor
    @Provides
    fun providesApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()

            val newUrl = request.url.newBuilder()
                .addQueryParameter(BuildConfig.APP_ID_HEADER, BuildConfig.APP_ID).build()

            val newRequest = request.newBuilder().url(newUrl).build()

            chain.proceed(newRequest)
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
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun providesRetrofitHttpClient(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): HttpClient.RetrofitClient = RetrofitClientImpl(testUrl, okHttpClient, gsonConverterFactory)
}
