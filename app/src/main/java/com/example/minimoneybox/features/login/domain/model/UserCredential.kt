package com.example.minimoneybox.features.login.domain.model

data class UserCredential(
    val name: String,
    val token: String,
    val expiresIn: Int,
    val lastActivityTimestamp: Long,
)
