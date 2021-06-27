package com.example.minimoneybox.features.login.domain.usecases

import com.example.minimoneybox.common.data.networking.exceptions.DefaultNetworkError
import com.example.minimoneybox.features.login.domain.model.LoginParams
import com.example.minimoneybox.features.login.domain.repository.LoginRepository
import com.example.minimoneybox.features.login.domain.usecase.LoginUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

class LoginUseCaseTest {

    private val loginRepository: LoginRepository by lazy { mockk() }
    private val loginUseCase: LoginUseCase by lazy { LoginUseCase(loginRepository) }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `SHOULD invoke do not throw an Exception WHEN is invoked`() {
        coEvery { loginRepository.authenticate(any()) } returns Unit

        runBlocking {
            loginUseCase.invoke(LoginParams("email@email.com", "1234", ""))
            coVerify(exactly = 1) { loginRepository.authenticate(any()) }
        }
    }

    @Test(expected = DefaultNetworkError::class)
    fun `SHOULD LoginUseCase throw DefaultNetworkError when LoginRepository is invoked`() {
        coEvery { loginRepository.authenticate(any()) } throws DefaultNetworkError(null)

        runBlocking { loginUseCase.invoke(LoginParams("email@email.com", "123", "")) }
    }

}