package com.example.minimoneybox.features.account.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.common.data.networking.exceptions.SessionExpiredException
import com.example.minimoneybox.common.presentation.viewmodel.StateViewModel
import com.example.minimoneybox.features.account.domain.usecase.FetchAccountInformationUseCase
import com.example.minimoneybox.features.account.presentation.viewmodel.action.AccountAction
import com.example.minimoneybox.features.account.presentation.viewmodel.state.AccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val fetchAccountInformationUseCase: FetchAccountInformationUseCase,
) : StateViewModel<AccountState, AccountAction>(AccountState()) {

    fun fetchAccountInformation() {
        viewModelScope.launch {
            setState { currentState -> currentState.showLoading() }
            setState { currentState -> currentState.hideContent() }
            runCatching {
                fetchAccountInformationUseCase()
            }.onSuccess {
                setState { currentState -> currentState.showContent() }
                sendAction { AccountAction.ShowAccountInformationOnUI(it) }
            }.onFailure { throwable ->
                sendAction {
                    when (throwable) {
                        is SessionExpiredException -> AccountAction.LogoutUser
                        else -> AccountAction.OpenErrorScreen(throwable.message.orEmpty())
                    }
                }
            }
            setState { currentState -> currentState.hideLoading() }
        }
    }
}
