package com.example.minimoneybox.common.networking.retrofit

import com.example.minimoneybox.common.networking.HttpClient
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

class RetrofitClientImpl(
    private val baseUrl: String,
    private val okHttpClient: OkHttpClient,
    private val converterFactory: Converter.Factory,
) : HttpClient.RetrofitClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    override fun <T> create(c: Class<T>): T = retrofit.create(c)
}
