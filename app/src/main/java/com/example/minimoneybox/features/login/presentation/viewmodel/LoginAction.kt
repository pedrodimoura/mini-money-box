package com.example.minimoneybox.features.login.presentation.viewmodel

import com.example.minimoneybox.common.presentation.viewmodel.UIAction

sealed class LoginAction : UIAction {
    object OpenAccountsScreen : LoginAction()
    data class OpenErrorScreen(val message: String) : LoginAction()
}
