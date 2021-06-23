package com.example.minimoneybox.features.login.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class AuthenticationRequest(
    @SerializedName("Email")
    val email: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("Idfa")
    val idfa: String,
)
