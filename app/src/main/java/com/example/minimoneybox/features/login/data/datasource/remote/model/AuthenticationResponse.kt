package com.example.minimoneybox.features.login.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    @SerializedName("Session")
    val session: SessionMetadataResponse,
)
