package com.example.minimoneybox.features.account.presentation.viewmodel

import com.example.minimoneybox.common.presentation.viewmodel.UIState

data class AccountState(
    val isLoading: Boolean = false
) : UIState {
    fun showLoading() = this.copy(isLoading = true)
    fun hideLoading() = this.copy(isLoading = false)
}
