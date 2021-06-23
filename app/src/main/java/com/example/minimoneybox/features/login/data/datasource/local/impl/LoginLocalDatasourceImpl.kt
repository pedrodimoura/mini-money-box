package com.example.minimoneybox.features.login.data.datasource.local.impl

import com.example.minimoneybox.common.storage.Storage
import com.example.minimoneybox.features.login.data.datasource.local.LoginLocalDatasource
import com.example.minimoneybox.features.login.domain.model.UserCredential
import com.google.gson.Gson

private const val USER_CREDENTIAL = "user-credential"

class LoginLocalDatasourceImpl(
    private val preferences: Storage.Preferences,
    private val gson: Gson,
) : LoginLocalDatasource {

    override suspend fun save(userCredential: UserCredential) =
        preferences.saveString(USER_CREDENTIAL, gson.toJson(userCredential))

    override suspend fun get(): UserCredential {
        val userCredentialJson =
            preferences.getString(USER_CREDENTIAL) ?: throw IllegalStateException()
        return gson.fromJson(userCredentialJson, UserCredential::class.java)

    }

    override suspend fun isSessionActive(): Boolean {
        val userCredential = get()
        return (System.currentTimeMillis()
            .minus(userCredential.lastActivityTimestamp) > userCredential.expiresIn)
    }
}