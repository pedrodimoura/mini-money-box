package com.example.minimoneybox.features.login.data.datasource.remote

import com.example.minimoneybox.features.login.data.datasource.remote.impl.LoginRemoteDatasourceImpl
import com.example.minimoneybox.features.login.data.datasource.remote.model.AuthenticationRequest
import com.example.minimoneybox.features.login.data.datasource.remote.model.AuthenticationResponse
import com.example.minimoneybox.features.login.data.datasource.remote.model.SessionMetadataResponse
import com.example.minimoneybox.features.login.data.service.LoginService
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class LoginRemoteDatasourceTest {

    private val loginService: LoginService by lazy { mockk() }

    private val loginRemoteDataSource: LoginRemoteDatasource by lazy {
        LoginRemoteDatasourceImpl(loginService)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `SHOULD authenticate from LoginRemoteDatasource be successful WHEN is invoked`() {
        runBlocking {
            val mockRequest = mockk<AuthenticationRequest>()
            val expectedResult = AuthenticationResponse(
                SessionMetadataResponse("bearer-token", 10)
            )
            coEvery { loginService.authenticate(any()) } returns expectedResult

            val result = loginRemoteDataSource.authenticate(mockRequest)

            assertEquals(expectedResult, result)
        }
    }

    @Test(expected = HttpException::class)
    fun `SHOULD authenticate from LoginRemoteDatasource thrown Exception WHEN is invoked`() {
        runBlocking {
            val mockRequest = mockk<AuthenticationRequest>()
            coEvery { loginService.authenticate(any()) } throws HttpException(
                Response.error<HttpException>(
                    404,
                    "".toResponseBody("application/json".toMediaType())
                )
            )

            loginRemoteDataSource.authenticate(mockRequest)
        }
    }

}