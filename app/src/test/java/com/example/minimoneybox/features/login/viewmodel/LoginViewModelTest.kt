package com.example.minimoneybox.features.login.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.minimoneybox.common.presentation.validation.Validator
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
    private val emailValidator: Validator<String> by lazy { mockk() }
    private val loginViewModel: LoginViewModel by lazy {
        LoginViewModel(loginUseCase, emailValidator)
    }

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
        every { emailValidator.validate(any()) } returns true

        loginViewModel.authenticate(LoginParams("email@email.com", "1234", ""))

        verifySequence {
            statesObserver.onChanged(LoginState(isLoading = false))
            statesObserver.onChanged(LoginState(isLoading = false))
            statesObserver.onChanged(LoginState(isLoading = false))
            statesObserver.onChanged(LoginState(isLoading = false))
            statesObserver.onChanged(LoginState(isLoading = true))
            actionsObserver.onChanged(LoginAction.OpenAccountsScreen)
            statesObserver.onChanged(LoginState(isLoading = false))
        }
    }

    @Test
    fun `SHOULD authenticate be not invoked WHEN email is invalid`() {
        every { emailValidator.validate(any()) } returns false

        loginViewModel.authenticate(LoginParams("not_an_email", "1234", ""))

        verifySequence {
            statesObserver.onChanged(LoginState(isLoading = false))
            statesObserver.onChanged(LoginState(isLoading = false))
            statesObserver.onChanged(LoginState(isLoading = false, isEmailInvalid = true))
        }
    }

    @Test
    fun `SHOULD authenticate be not invoked WHEN password is empty`() {
        every { emailValidator.validate(any()) } returns true

        loginViewModel.authenticate(LoginParams("email@email.com", "", ""))

        verifySequence {
            statesObserver.onChanged(LoginState(isLoading = false))
            statesObserver.onChanged(LoginState(isLoading = false))
            statesObserver.onChanged(LoginState(isLoading = false, isEmailInvalid = false))
            statesObserver.onChanged(
                LoginState(isLoading = false, isEmailInvalid = false, isPasswordEmpty = true)
            )
        }
    }

}