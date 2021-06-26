package com.example.minimoneybox.features.account.presentation.viewmodel

import com.example.minimoneybox.common.presentation.viewmodel.UIAction
import com.example.minimoneybox.features.account.domain.model.AccountInformation

sealed class AccountAction : UIAction {
    data class ShowAccountInformationOnUI(
        val accountInformation: AccountInformation
    ) : AccountAction()

    data class OpenErrorScreen(val message: String) : AccountAction()
}
