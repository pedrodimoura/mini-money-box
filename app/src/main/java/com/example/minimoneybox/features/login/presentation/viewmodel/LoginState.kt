package com.example.minimoneybox.features.login.presentation.viewmodel

import com.example.minimoneybox.common.presentation.viewmodel.UIState

data class LoginState(
    val isLoading: Boolean = false,
    val isEmailInvalid: Boolean = false,
    val isPasswordEmpty: Boolean = false,
) : UIState {
    fun showLoading() = this.copy(isLoading = true)
    fun hideLoading() = this.copy(isLoading = false)

    fun emailInvalid() = this.copy(isEmailInvalid = true)
    fun emailValid() = this.copy(isEmailInvalid = false)

    fun passwordEmpty() = this.copy(isPasswordEmpty = true)
    fun passwordNotEmpty() = this.copy(isPasswordEmpty = false)

    fun clearPreviousErrorStates() = this.copy(isEmailInvalid = false, isPasswordEmpty = false)
}
