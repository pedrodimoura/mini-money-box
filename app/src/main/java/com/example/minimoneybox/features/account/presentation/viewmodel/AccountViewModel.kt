package com.example.minimoneybox.features.account.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.common.presentation.viewmodel.StateViewModel
import com.example.minimoneybox.features.account.domain.usecase.FetchAccountInformationUseCase
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

            runCatching {
                fetchAccountInformationUseCase()
            }.onSuccess {
                sendAction { AccountAction.ShowAccountInformationOnUI(it) }
            }.onFailure {
                sendAction { AccountAction.OpenErrorScreen(it.message.orEmpty()) }
            }

            setState { currentState -> currentState.hideLoading() }
        }
    }
}
