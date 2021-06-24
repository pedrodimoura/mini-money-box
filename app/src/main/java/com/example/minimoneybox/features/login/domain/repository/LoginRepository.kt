package com.example.minimoneybox.features.login.domain.repository

import com.example.minimoneybox.features.login.domain.exceptions.NotAuthorizedException
import com.example.minimoneybox.features.login.domain.model.LoginParams
import com.example.minimoneybox.features.login.domain.model.UserCredential

interface LoginRepository {
    @Throws(NotAuthorizedException::class)
    suspend fun authenticate(loginParams: LoginParams)

    suspend fun save(userCredential: UserCredential)
}
