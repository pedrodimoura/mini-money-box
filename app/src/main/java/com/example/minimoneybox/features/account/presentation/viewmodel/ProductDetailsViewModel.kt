package com.example.minimoneybox.features.account.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.common.data.networking.exceptions.SessionExpiredException
import com.example.minimoneybox.common.presentation.viewmodel.StateViewModel
import com.example.minimoneybox.features.account.domain.model.QuickAddParams
import com.example.minimoneybox.features.account.domain.usecase.QuickAddUseCase
import com.example.minimoneybox.features.account.presentation.viewmodel.action.ProductDetailsAction
import com.example.minimoneybox.features.account.presentation.viewmodel.state.ProductDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val quickAddUseCase: QuickAddUseCase,
) : StateViewModel<ProductDetailsState, ProductDetailsAction>(ProductDetailsState()) {

    fun quickAdd(amount: Double, productId: Int) {
        setState { currentState -> currentState.showLoading() }
        viewModelScope.launch {
            runCatching {
                quickAddUseCase.invoke(QuickAddParams(amount, productId))
            }.onSuccess {
                sendAction { ProductDetailsAction.UpdateMoneyboxAmount(it.moneyboxAmount) }
            }.onFailure { throwable ->
                sendAction {
                    when (throwable) {
                        is SessionExpiredException -> ProductDetailsAction.LogoutUser
                        else -> ProductDetailsAction.OpenErrorScreen(throwable.message.orEmpty())
                    }
                }
            }
            setState { currentState -> currentState.hideLoading() }
        }
    }

}