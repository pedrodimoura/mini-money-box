package com.example.minimoneybox.features.login.data.repository

import com.example.minimoneybox.common.networking.exceptions.localThrowableToDomain
import com.example.minimoneybox.common.networking.exceptions.remoteThrowableToDomain
import com.example.minimoneybox.features.login.data.datasource.local.LoginLocalDatasource
import com.example.minimoneybox.features.login.data.datasource.remote.LoginRemoteDatasource
import com.example.minimoneybox.features.login.data.datasource.remote.model.AuthenticationRequest
import com.example.minimoneybox.features.login.domain.model.LoginParams
import com.example.minimoneybox.features.login.domain.model.UserCredential
import com.example.minimoneybox.features.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginLocalDatasource: LoginLocalDatasource,
    private val loginRemoteDatasource: LoginRemoteDatasource,
) : LoginRepository {

    override suspend fun authenticate(loginParams: LoginParams) {
        runCatching {
            loginRemoteDatasource.authenticate(loginParams.mapToRemote())
        }.onSuccess { authenticationResponse ->
            val userCredential = UserCredential(
                loginParams.name.orEmpty(),
                authenticationResponse.session.bearerToken,
                authenticationResponse.session.expiresIn,
                System.currentTimeMillis()
            )
            this.save(userCredential)
        }.onFailure { throwable ->
            throw throwable.remoteThrowableToDomain()
        }
    }

    override suspend fun save(userCredential: UserCredential) {
        runCatching { loginLocalDatasource.save(userCredential) }
            .getOrElse { throwable -> throw throwable.localThrowableToDomain() }
    }

    private fun LoginParams.mapToRemote() =
        AuthenticationRequest(this.email, this.password, this.name.orEmpty())
}
