package com.example.minimoneybox.features.login.data.datasource.local.impl

import com.example.minimoneybox.common.di.NetworkHiltModule
import com.example.minimoneybox.common.storage.Storage
import com.example.minimoneybox.features.login.data.datasource.local.LoginLocalDatasource
import com.example.minimoneybox.features.login.domain.model.UserCredential
import com.google.gson.Gson
import javax.inject.Inject


class LoginLocalDatasourceImpl @Inject constructor(
    private val preferences: Storage.Preferences,
    private val gson: Gson,
) : LoginLocalDatasource {

    override suspend fun save(userCredential: UserCredential) =
        preferences.saveString(NetworkHiltModule.USER_CREDENTIAL, gson.toJson(userCredential))

    override suspend fun get(): UserCredential {
        val userCredentialJson =
            preferences.getString(NetworkHiltModule.USER_CREDENTIAL)
                ?: throw IllegalStateException()
        return gson.fromJson(userCredentialJson, UserCredential::class.java)

    }

    override suspend fun isSessionActive(): Boolean {
        val userCredential = get()
        return (System.currentTimeMillis()
            .minus(userCredential.lastActivityTimestamp) > userCredential.expiresIn)
    }
}