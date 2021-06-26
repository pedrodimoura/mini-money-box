package com.example.minimoneybox.features.account.domain

import com.example.minimoneybox.common.networking.exceptions.DefaultNetworkError
import com.example.minimoneybox.common.test.BaseUnitTest
import com.example.minimoneybox.features.account.domain.repository.AccountRepository
import com.example.minimoneybox.features.account.domain.usecase.FetchAccountInformationUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FetchAccountInformationUseCaseTest : BaseUnitTest() {

    private val accountRepository: AccountRepository by lazy { mockk() }

    private val fetchAccountInformationUseCase by lazy {
        FetchAccountInformationUseCase(accountRepository)
    }

    @Test
    fun `SHOULD FetchAccountInformationUseCase be successful`() {
        coEvery { accountRepository.fetchAccountInformation() } returns mockk()
        runBlocking { fetchAccountInformationUseCase() }
        coVerify(exactly = 1) { accountRepository.fetchAccountInformation() }
    }

    @Test(expected = DefaultNetworkError::class)
    fun `SHOULD FetchAccountInformationUseCase fail`() {
        coEvery { accountRepository.fetchAccountInformation() } throws DefaultNetworkError(null)
        runBlocking { fetchAccountInformationUseCase() }
        coVerify(exactly = 0) { accountRepository.fetchAccountInformation() }
    }

}