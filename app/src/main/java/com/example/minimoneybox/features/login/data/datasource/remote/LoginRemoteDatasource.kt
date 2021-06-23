package com.example.minimoneybox.features.login.data.datasource.remote

import com.example.minimoneybox.features.login.data.datasource.remote.model.AuthenticationRequest
import com.example.minimoneybox.features.login.data.datasource.remote.model.AuthenticationResponse

interface LoginRemoteDatasource {
    suspend fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse
}
