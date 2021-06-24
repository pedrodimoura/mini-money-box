package com.example.minimoneybox.features.login.domain.model

data class LoginParams(
    val email: String,
    val password: String,
    val name: String?
)
