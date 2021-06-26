package com.example.minimoneybox.features.account.data.datasource.remote.service

import com.example.minimoneybox.common.networking.RetrofitClientTest
import com.example.minimoneybox.common.test.MockWebServerTest
import com.example.minimoneybox.features.account.data.datasource.remote.model.AccountInformationResponse
import com.example.minimoneybox.features.account.data.datasource.remote.service.AccountService
import com.google.gson.Gson
import java.net.HttpURLConnection
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException

private const val ACCOUNT_INFORMATION_RESPONSE_JSON = "account_information_response.json"

class AccountServiceTest : MockWebServerTest() {

    private val gson: Gson by lazy { Gson() }

    private val retrofitClientTest: RetrofitClientTest by lazy {
        RetrofitClientTest(mockWebServer)
    }

    private val accountService: AccountService by lazy {
        retrofitClientTest.create(AccountService::class.java)
    }

    @Test
    fun `SHOULD fetchAccountInformation be successful`() {
        val responseJson = readFile(ACCOUNT_INFORMATION_RESPONSE_JSON)
        val expected = gson.fromJson(responseJson, AccountInformationResponse::class.java)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(responseJson)
        )

        runBlocking {
            val result = accountService.fetchAccountInformation()
            assertEquals(expected, result)
        }
    }

    @Test(expected = HttpException::class)
    fun `SHOULD fetchAccountInformation throw SocketTimeout`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
        )

        runBlocking { accountService.fetchAccountInformation() }
    }
}
