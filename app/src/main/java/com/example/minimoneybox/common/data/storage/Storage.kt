package com.example.minimoneybox.common.data.storage

sealed interface Storage {
    interface Preferences {
        fun saveString(key: String, value: String)
        fun getString(key: String, defaultValue: String? = null): String?
        fun saveBoolean(key: String, value: Boolean)
        fun getBoolean(key: String, defaultValue: Boolean): Boolean
        fun invalidate(key: String)
        fun invalidateAll()
    }

    interface Database
}