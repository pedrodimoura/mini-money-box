package com.example.minimoneybox.features.account.presentation.viewmodel.state

import com.example.minimoneybox.common.presentation.viewmodel.UIState

data class AccountState(
    val isLoading: Boolean = false,
    val isContentVisible: Boolean = false,
) : UIState {
    fun showLoading() = this.copy(isLoading = true)
    fun hideLoading() = this.copy(isLoading = false)
    fun showContent() = this.copy(isContentVisible = true)
    fun hideContent() = this.copy(isContentVisible = false)
}
