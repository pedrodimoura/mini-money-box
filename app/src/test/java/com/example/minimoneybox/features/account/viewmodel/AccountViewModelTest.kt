package com.example.minimoneybox.features.account.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.minimoneybox.common.data.networking.exceptions.DefaultNetworkError
import com.example.minimoneybox.features.account.domain.model.AccountInformation
import com.example.minimoneybox.features.account.domain.usecase.FetchAccountInformationUseCase
import com.example.minimoneybox.features.account.presentation.viewmodel.AccountAction
import com.example.minimoneybox.features.account.presentation.viewmodel.AccountState
import com.example.minimoneybox.features.account.presentation.viewmodel.AccountViewModel
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AccountViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val fetchAccountInformationUseCase: FetchAccountInformationUseCase by lazy { mockk() }

    private val actionsObserver: Observer<AccountAction> = mockk()
    private val statesObserver: Observer<AccountState> = mockk()

    private val accountViewModel: AccountViewModel by lazy {
        AccountViewModel(fetchAccountInformationUseCase)
    }

    @Before
    fun setup() {
        every { actionsObserver.onChanged(any()) } just Runs
        every { statesObserver.onChanged(any()) } just Runs
        accountViewModel.states.observeForever(statesObserver)
        accountViewModel.actions.observeForever(actionsObserver)
    }

    @After
    fun tearDown() {
        accountViewModel.states.removeObserver(statesObserver)
        accountViewModel.actions.removeObserver(actionsObserver)
        clearAllMocks()
    }

    @Test
    fun `SHOULD fetchAccountInformation emits Show Loading, Hide Content, Show Data, Show Content and Hide Loading`() {
        val mockReturn: AccountInformation = mockk()
        coEvery { fetchAccountInformationUseCase() } returns mockReturn

        accountViewModel.fetchAccountInformation()

        verifySequence {
            statesObserver.onChanged(AccountState())
            statesObserver.onChanged(AccountState(isLoading = true))
            statesObserver.onChanged(AccountState(isLoading = true, isContentVisible = false))
            statesObserver.onChanged(AccountState(isLoading = true, isContentVisible = true))
            actionsObserver.onChanged(AccountAction.ShowAccountInformationOnUI(mockReturn))
            statesObserver.onChanged(AccountState(isLoading = false, isContentVisible = true))
        }
    }

    @Test
    fun `SHOULD fetchAccountInformation emits Show Loading, Hide Content, OpenErrorScreen and Hide Loading`() {
        val expectedError = DefaultNetworkError(null)
        coEvery { fetchAccountInformationUseCase() } throws expectedError

        accountViewModel.fetchAccountInformation()

        verifySequence {
            statesObserver.onChanged(AccountState())
            statesObserver.onChanged(AccountState(isLoading = true, isContentVisible = false))
            statesObserver.onChanged(AccountState(isLoading = true, isContentVisible = false))
            actionsObserver.onChanged(AccountAction.OpenErrorScreen(expectedError.message.orEmpty()))
            statesObserver.onChanged(AccountState(isLoading = false, isContentVisible = false))
        }
    }

}
