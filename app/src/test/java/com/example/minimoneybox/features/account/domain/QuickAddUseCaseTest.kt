package com.example.minimoneybox.features.account.domain

import com.example.minimoneybox.common.data.networking.exceptions.DefaultNetworkError
import com.example.minimoneybox.common.test.BaseUnitTest
import com.example.minimoneybox.features.account.domain.repository.AccountRepository
import com.example.minimoneybox.features.account.domain.usecase.QuickAddUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class QuickAddUseCaseTest : BaseUnitTest() {

    private val accountRepository: AccountRepository by lazy { mockk() }

    private val quickAddUseCase: QuickAddUseCase by lazy { QuickAddUseCase(accountRepository) }

    @Test
    fun `SHOULD quickAddUseCase be successful`() {
        coEvery { accountRepository.quickAdd(any()) } returns mockk()
        runBlocking { quickAddUseCase.invoke(mockk()) }
        coVerify(exactly = 1) { accountRepository.quickAdd(any()) }
    }

    @Test(expected = DefaultNetworkError::class)
    fun `SHOULD quickAddUseCase fail`() {
        coEvery { accountRepository.quickAdd(any()) } throws DefaultNetworkError(null)
        runBlocking { quickAddUseCase.invoke(mockk()) }
        coVerify(exactly = 1) { accountRepository.quickAdd(any()) }
    }

}