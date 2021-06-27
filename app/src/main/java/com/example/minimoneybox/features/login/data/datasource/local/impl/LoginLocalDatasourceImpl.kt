package com.example.minimoneybox.features.login.data.datasource.local.impl

import com.example.minimoneybox.common.domain.SessionRepository
import com.example.minimoneybox.common.domain.model.UserCredential
import com.example.minimoneybox.features.login.data.datasource.local.LoginLocalDatasource
import javax.inject.Inject


class LoginLocalDatasourceImpl @Inject constructor(
    private val sessionRepository: SessionRepository,
) : LoginLocalDatasource {

    override suspend fun save(userCredential: UserCredential) =
        sessionRepository.save(userCredential)

    override suspend fun get(): UserCredential = sessionRepository.get()

    override suspend fun isSessionActive(): Boolean {
        val userCredential = get()
        return (System.currentTimeMillis()
            .minus(userCredential.lastActivityTimestamp) > userCredential.expiresIn)
    }

    override fun logout() = sessionRepository.invalidate()
}