package com.example.minimoneybox.features.account.data.datasource.remote

import com.example.minimoneybox.common.test.BaseUnitTest
import com.example.minimoneybox.features.account.data.datasource.remote.impl.AccountRemoteDatasourceImpl
import com.example.minimoneybox.features.account.data.datasource.remote.model.AccountInformationResponse
import com.example.minimoneybox.features.account.data.datasource.remote.model.OneOffPaymentsRequest
import com.example.minimoneybox.features.account.data.datasource.remote.model.OneOffPaymentsResponse
import com.example.minimoneybox.features.account.data.datasource.remote.service.AccountService
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.net.HttpURLConnection
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

private const val ACCOUNT_INFORMATION_RESPONSE_JSON = "account_information_response.json"
private const val ONE_OFF_PAYMENTS_RESPONSE_JSON = "one_off_payments_response.json"

class AccountRemoteDatasourceTest : BaseUnitTest() {

    private val gson: Gson by lazy { Gson() }
    private val accountService: AccountService by lazy { mockk() }

    private val accountRemoteDatasource: AccountRemoteDatasource by lazy {
        AccountRemoteDatasourceImpl(accountService)
    }

    @Test
    fun `SHOULD fetchAccountInformation be successful`() {
        val responseJson = readFile(ACCOUNT_INFORMATION_RESPONSE_JSON)
        val expected =
            gson.fromJson(responseJson, AccountInformationResponse::class.java)
        coEvery { accountService.fetchAccountInformation() } returns expected

        runBlocking {
            val result = accountRemoteDatasource.fetchAccountInformation()

            coVerify(exactly = 1) { accountService.fetchAccountInformation() }

            assertEquals(expected, result)
        }
    }

    @Test(expected = HttpException::class)
    fun `SHOULD fetchAccountInformation fail`() {
        coEvery { accountService.fetchAccountInformation() } throws HttpException(
            Response.error<AccountInformationResponse>(
                HttpURLConnection.HTTP_GATEWAY_TIMEOUT,
                "".toResponseBody("application/json".toMediaType())
            )
        )

        runBlocking {
            accountRemoteDatasource.fetchAccountInformation()
            coVerify(exactly = 1) { accountService.fetchAccountInformation() }
        }
    }

    @Test
    fun `SHOULD oneOffPayments be successful`() {
        val responseJson = readFile(ONE_OFF_PAYMENTS_RESPONSE_JSON)
        val expected =
            gson.fromJson(responseJson, OneOffPaymentsResponse::class.java)

        coEvery { accountService.oneOffPayments(any()) } returns expected

        val oneOffPaymentsRequest = OneOffPaymentsRequest(
            amount = 10.0,
            investorProductId = 1
        )

        runBlocking {
            val result = accountRemoteDatasource.oneOffPayments(oneOffPaymentsRequest)
            coVerify(exactly = 1) { accountService.oneOffPayments(any()) }
            assertEquals(expected, result)
        }
    }

}
