package com.example.minimoneybox.features.login.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.common.di.EmailValidatorType
import com.example.minimoneybox.common.presentation.validation.Validator
import com.example.minimoneybox.common.presentation.viewmodel.StateViewModel
import com.example.minimoneybox.features.login.domain.model.LoginParams
import com.example.minimoneybox.features.login.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    @EmailValidatorType private val emailValidator: Validator<String>,
) : StateViewModel<LoginState, LoginAction>(LoginState()) {

    fun authenticate(loginParams: LoginParams) {
        if (loginParams.isValid())
            proceed(loginParams)
    }

    private fun proceed(loginParams: LoginParams) {
        setState { currentState -> currentState.showLoading() }
        viewModelScope.launch {
            runCatching { loginUseCase(loginParams) }
                .onSuccess {
                    sendAction { LoginAction.OpenAccountsScreen }
                }.onFailure {
                    sendAction { LoginAction.OpenErrorScreen(it.message.orEmpty()) }
                }
            setState { currentState -> currentState.hideLoading() }
        }
    }

    fun LoginParams.isValid(): Boolean {

        setState { currentState -> currentState.clearPreviousErrorStates() }

        if (emailValidator.validate(email).not()) {
            setState { currentState -> currentState.emailInvalid() }
            return false
        } else setState { currentState -> currentState.emailValid() }

        if (password.isEmpty()) {
            setState { currentState -> currentState.passwordEmpty() }
            return false
        } else setState { currentState -> currentState.passwordNotEmpty() }

        return true
    }
}
