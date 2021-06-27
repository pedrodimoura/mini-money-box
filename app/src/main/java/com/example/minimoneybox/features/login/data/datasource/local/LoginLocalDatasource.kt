package com.example.minimoneybox.features.login.data.datasource.local

import com.example.minimoneybox.common.domain.model.UserCredential

interface LoginLocalDatasource {

    suspend fun save(userCredential: UserCredential)

    suspend fun get(): UserCredential

    suspend fun isSessionActive(): Boolean

    fun logout()

}