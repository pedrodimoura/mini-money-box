package com.example.minimoneybox.features.account.viewmodel

import androidx.lifecycle.Observer
import com.example.minimoneybox.common.test.BaseUnitTest
import com.example.minimoneybox.features.account.domain.model.QuickAddResult
import com.example.minimoneybox.features.account.domain.usecase.QuickAddUseCase
import com.example.minimoneybox.features.account.presentation.viewmodel.ProductDetailsViewModel
import com.example.minimoneybox.features.account.presentation.viewmodel.state.ProductDetailsState
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProductDetailsViewModelTest : BaseUnitTest() {

    private val quickAddUseCase: QuickAddUseCase by lazy { mockk() }

    private val viewModel: ProductDetailsViewModel by lazy {
        ProductDetailsViewModel(quickAddUseCase)
    }

    private val statesObserver: Observer<ProductDetailsState> by lazy { mockk() }

    @Before
    fun setup() {
        every { statesObserver.onChanged(any()) } just Runs
        viewModel.states.observeForever(statesObserver)
    }

    @After
    fun tearDown() {
        viewModel.states.removeObserver(statesObserver)
        clearAllMocks()
    }

    @Test
    fun `SHOULD quickAdd be successful`() {
        val quickAddResult: QuickAddResult = mockk(relaxed = true)
        coEvery { quickAddUseCase.invoke(any()) } returns quickAddResult

        val amount = 10.0
        val productId = 1

        viewModel.quickAdd(amount, productId)
    }

}
