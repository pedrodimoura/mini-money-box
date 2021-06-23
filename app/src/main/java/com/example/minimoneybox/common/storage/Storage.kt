package com.example.minimoneybox.common.storage

sealed interface Storage {
    interface Preferences {
        fun saveString(key: String, value: String)
        fun getString(key: String, defaultValue: String? = null): String?
    }

    interface Database
}