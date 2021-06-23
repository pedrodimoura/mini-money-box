package com.example.minimoneybox.features.login.data.service

import com.example.minimoneybox.features.login.data.datasource.remote.model.AuthenticationRequest
import com.example.minimoneybox.features.login.data.datasource.remote.model.AuthenticationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("users/login")
    suspend fun authenticate(
        @Body authenticationRequest: AuthenticationRequest
    ): AuthenticationResponse

}