package com.example.minimoneybox.features.account.data.repository

import com.example.minimoneybox.common.networking.exceptions.DefaultNetworkError
import com.example.minimoneybox.common.test.BaseUnitTest
import com.example.minimoneybox.features.account.data.datasource.remote.AccountRemoteDatasource
import com.example.minimoneybox.features.account.data.datasource.remote.model.AccountInformationResponse
import com.example.minimoneybox.features.account.domain.repository.AccountRepository
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

private const val ACCOUNT_INFORMATION_RESPONSE_JSON = "account_information_response.json"

class AccountRepositoryTest : BaseUnitTest() {

    private val gson: Gson by lazy { Gson() }

    private val accountRemoteDatasource: AccountRemoteDatasource by lazy { mockk() }

    private val accountRepository: AccountRepository by lazy {
        AccountRepositoryImpl(accountRemoteDatasource)
    }

    @Test
    fun `SHOULD fetchAccountInformation be successful`() {
        val json = readFile(ACCOUNT_INFORMATION_RESPONSE_JSON)
        val accountInformationResponse = gson.fromJson(json, AccountInformationResponse::class.java)

        coEvery {
            accountRemoteDatasource.fetchAccountInformation()
        } returns accountInformationResponse

        runBlocking {
            val result = accountRepository.fetchAccountInformation()
            coVerify(exactly = 1) { accountRemoteDatasource.fetchAccountInformation() }
            assertEquals(accountInformationResponse.asDomain(), result)
        }
    }

    @Test(expected = DefaultNetworkError::class)
    fun `SHOULD fetchAccountInformation fail`() {
        coEvery {
            accountRemoteDatasource.fetchAccountInformation()
        } throws DefaultNetworkError(null)

        runBlocking {
            accountRepository.fetchAccountInformation()
            coVerify(exactly = 0) { accountRemoteDatasource.fetchAccountInformation() }
        }
    }

}
