package com.example.minimoneybox.features.login.data.service

import com.example.minimoneybox.common.data.networking.HttpClient
import com.example.minimoneybox.common.networking.RetrofitClientTest
import com.example.minimoneybox.common.test.MockWebServerTest
import com.example.minimoneybox.features.login.data.datasource.remote.model.AuthenticationRequest
import com.example.minimoneybox.features.login.data.datasource.remote.service.LoginService
import java.io.InterruptedIOException
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class LoginServiceTest : MockWebServerTest() {

    private val httpClient: HttpClient.RetrofitClient by lazy { RetrofitClientTest(mockWebServer) }

    private val loginService: LoginService by lazy { httpClient.create(LoginService::class.java) }

    @Test
    fun `SHOULD authenticate be successful WHEN LoginService authenticate is invoked`() {
        runBlocking {
            val mock200Response = MockResponse().setBody(readFile("authentication_response.json"))
            mockWebServer.enqueue(mock200Response)

            val authenticationRequest = AuthenticationRequest(
                email = "pdimoura@gmail.com",
                password = "123",
                idfa = "test"
            )

            val authenticationResult = loginService.authenticate(authenticationRequest)

            assertEquals(
                "TsMWRkbrcu3NGrpf84gi2+pg0iOMVymyKklmkY0oI84=",
                authenticationResult.session.bearerToken
            )
        }
    }

    @Test(expected = InterruptedIOException::class)
    fun `SHOULD authenticate timeout WHEN LoginService authenticate is invoked`() {
        runBlocking {
            val mock200Response = MockResponse()
                .setBodyDelay(4, TimeUnit.SECONDS)
                .setBody(readFile("authentication_response.json"))

            mockWebServer.enqueue(mock200Response)

            val authenticationRequest = AuthenticationRequest(
                email = "pdimoura@gmail.com",
                password = "123",
                idfa = "test"
            )

            loginService.authenticate(authenticationRequest)
        }
    }

}