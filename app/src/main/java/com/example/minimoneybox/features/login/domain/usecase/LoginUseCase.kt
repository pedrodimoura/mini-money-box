package com.example.minimoneybox.features.login.domain.usecase

import com.example.minimoneybox.features.login.domain.model.LoginParams
import com.example.minimoneybox.features.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    suspend operator fun invoke(loginParams: LoginParams) =
        loginRepository.authenticate(loginParams)
}
