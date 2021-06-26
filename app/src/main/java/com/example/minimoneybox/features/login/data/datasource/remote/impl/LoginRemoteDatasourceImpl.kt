package com.example.minimoneybox.features.login.data.datasource.remote.impl

import com.example.minimoneybox.features.login.data.datasource.remote.LoginRemoteDatasource
import com.example.minimoneybox.features.login.data.datasource.remote.model.AuthenticationRequest
import com.example.minimoneybox.features.login.data.datasource.remote.model.AuthenticationResponse
import com.example.minimoneybox.features.login.data.datasource.remote.service.LoginService
import javax.inject.Inject

class LoginRemoteDatasourceImpl @Inject constructor(
    private val loginService: LoginService,
) : LoginRemoteDatasource {

    override suspend fun authenticate(
        authenticationRequest: AuthenticationRequest
    ): AuthenticationResponse = loginService.authenticate(authenticationRequest)
}
