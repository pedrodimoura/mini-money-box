package com.example.minimoneybox.features.login.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class SessionMetadataResponse(
    @SerializedName("BearerToken")
    val bearerToken: String, // This comma it helps to avoid unnecessary changes in Pull Request to review.
)
