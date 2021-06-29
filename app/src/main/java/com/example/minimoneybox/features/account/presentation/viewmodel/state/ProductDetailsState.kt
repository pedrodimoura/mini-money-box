package com.example.minimoneybox.features.account.presentation.viewmodel.state

import com.example.minimoneybox.common.presentation.viewmodel.UIState

data class ProductDetailsState(
    val isLoading: Boolean = false,
) : UIState {
    fun showLoading() = this.copy(isLoading = true)
    fun hideLoading() = this.copy(isLoading = false)
}
