package com.example.minimoneybox.common.data.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.minimoneybox.common.data.storage.Storage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val PREFS_NAME = "mini-money-box-preferences"

class PreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : Storage.Preferences {

    private val masterKey = MasterKey
        .Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedSharedPreferences: SharedPreferences by lazy {
        EncryptedSharedPreferences
            .create(
                context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }

    override fun saveString(key: String, value: String) =
        encryptedSharedPreferences
            .edit()
            .putString(key, value)
            .apply()

    override fun getString(key: String, defaultValue: String?): String? =
        encryptedSharedPreferences.getString(key, defaultValue)

    override fun saveBoolean(key: String, value: Boolean) =
        encryptedSharedPreferences.edit().putBoolean(key, value).apply()

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        encryptedSharedPreferences.getBoolean(key, defaultValue)

    override fun invalidate(key: String) =
        encryptedSharedPreferences.edit().remove(key).apply()

    override fun invalidateAll() = encryptedSharedPreferences.edit().clear().apply()
}