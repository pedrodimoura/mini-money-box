package com.example.minimoneybox.features.login.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.common.presentation.viewmodel.StateViewModel
import com.example.minimoneybox.features.login.domain.model.LoginParams
import com.example.minimoneybox.features.login.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : StateViewModel<LoginState, LoginAction>(LoginState()) {

    fun authenticate(loginParams: LoginParams) {
        viewModelScope.launch {
            setState { currentState -> currentState.showLoading() }

            runCatching { loginUseCase(loginParams) }
                .onSuccess {
                    sendAction { LoginAction.OpenAccountsScreen }
                }.onFailure {
                    sendAction { LoginAction.OpenErrorScreen(it.message.orEmpty()) }
                }

            setState { currentState -> currentState.hideLoading() }
        }
    }
}
