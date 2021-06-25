package com.example.minimoneybox.features.login.data.service

import com.example.minimoneybox.features.login.data.datasource.remote.model.AuthenticationRequest
import com.example.minimoneybox.features.login.data.datasource.remote.model.AuthenticationResponse
import retrofit2.http.Body
import retrofit2.http.POST

const val USERS_LOGIN_ENDPOINT = "users/login"

interface LoginService {

    @POST(USERS_LOGIN_ENDPOINT)
    suspend fun authenticate(
        @Body authenticationRequest: AuthenticationRequest
    ): AuthenticationResponse
}
