package com.example.minimoneybox.features.login.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.minimoneybox.common.data.networking.exceptions.DefaultNetworkError
import com.example.minimoneybox.features.login.domain.model.LoginParams
import com.example.minimoneybox.features.login.domain.usecase.LoginUseCase
import com.example.minimoneybox.features.login.presentation.viewmodel.LoginAction
import com.example.minimoneybox.features.login.presentation.viewmodel.LoginState
import com.example.minimoneybox.features.login.presentation.viewmodel.LoginViewModel
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val loginUseCase: LoginUseCase by lazy { mockk() }
    private val loginViewModel: LoginViewModel by lazy { LoginViewModel(loginUseCase) }

    private val statesObserver: Observer<LoginState> = mockk()
    private val actionsObserver: Observer<LoginAction> = mockk()

    @Before
    fun setup() {
        every { statesObserver.onChanged(any()) } just Runs
        every { actionsObserver.onChanged(any()) } just Runs
        loginViewModel.states.observeForever(statesObserver)
        loginViewModel.actions.observeForever(actionsObserver)
    }

    @After
    fun tearDown() {
        loginViewModel.states.removeObserver(statesObserver)
        loginViewModel.actions.removeObserver(actionsObserver)
        clearAllMocks()
    }

    @Test
    fun `SHOULD authenticate be invoked and emmit showLoading state, OpenAccountsScreen action and hideLoading state`() {
        coEvery { loginUseCase(any()) } returns Unit

        loginViewModel.authenticate(LoginParams("email@email.com", "1234", ""))

        verifySequence {
            statesObserver.onChanged(LoginState(isLoading = false))
            statesObserver.onChanged(LoginState(isLoading = true))
            actionsObserver.onChanged(LoginAction.OpenAccountsScreen)
            statesObserver.onChanged(LoginState(isLoading = false))
        }
    }

    @Test
    fun `SHOULD authenticate be invoked and emmit showLoading state, OpenErrorScreen action and hideLoading state`() {
        val expectedError = DefaultNetworkError(null)
        coEvery { loginUseCase(any()) } throws DefaultNetworkError(null)

        loginViewModel.authenticate(LoginParams("email@email.com", "1234", ""))

        verifySequence {
            statesObserver.onChanged(LoginState(isLoading = false))
            statesObserver.onChanged(LoginState(isLoading = true))
            actionsObserver.onChanged(LoginAction.OpenErrorScreen(expectedError.message.orEmpty()))
            statesObserver.onChanged(LoginState(isLoading = false))
        }
    }

}