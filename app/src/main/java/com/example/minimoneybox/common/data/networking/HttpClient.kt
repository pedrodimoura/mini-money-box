package com.example.minimoneybox.common.data.networking

sealed interface HttpClient {
    interface RetrofitClient : HttpClient {
        fun <T> create(c: Class<T>): T
    }
}