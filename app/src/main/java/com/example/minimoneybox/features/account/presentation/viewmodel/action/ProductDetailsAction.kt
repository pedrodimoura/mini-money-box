package com.example.minimoneybox.features.account.presentation.viewmodel.action

import com.example.minimoneybox.common.presentation.viewmodel.UIAction

sealed class ProductDetailsAction : UIAction {
    data class UpdateMoneyboxAmount(val amount: Double) : ProductDetailsAction()
    data class OpenErrorScreen(val message: String) : ProductDetailsAction()
    object LogoutUser : ProductDetailsAction()
}
