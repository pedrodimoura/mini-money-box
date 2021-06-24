package com.example.minimoneybox.features.login.data.repository

import com.example.minimoneybox.common.networking.exceptions.ResourceNotFoundException
import com.example.minimoneybox.common.networking.exceptions.TypeConversionException
import com.example.minimoneybox.features.login.data.datasource.local.LoginLocalDatasource
import com.example.minimoneybox.features.login.data.datasource.remote.LoginRemoteDatasource
import com.example.minimoneybox.features.login.data.datasource.remote.model.AuthenticationResponse
import com.example.minimoneybox.features.login.data.datasource.remote.model.SessionMetadataResponse
import com.example.minimoneybox.features.login.domain.model.LoginParams
import com.example.minimoneybox.features.login.domain.repository.LoginRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class LoginRepositoryTest {

    private val loginLocalDatasource: LoginLocalDatasource by lazy { mockk() }
    private val loginRemoteDatasource: LoginRemoteDatasource by lazy { mockk() }

    private val loginRepository: LoginRepository by lazy {
        LoginRepositoryImpl(loginLocalDatasource, loginRemoteDatasource)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `SHOULD LoginDatasource authenticate and save method be called once WHEN authenticate is invoked`() {
        val expectedResponse = AuthenticationResponse(
            SessionMetadataResponse(
                bearerToken = "",
                expiresIn = 10
            )
        )

        coEvery { loginLocalDatasource.save(any()) } returns Unit
        coEvery { loginRemoteDatasource.authenticate(any()) } returns expectedResponse

        runBlocking {
            loginRepository.authenticate(LoginParams("email@email.com", "1234", ""))
            coVerify(exactly = 1) { loginRemoteDatasource.authenticate(any()) }
            coVerify(exactly = 1) { loginLocalDatasource.save(any()) }
        }
    }

    @Test(expected = ResourceNotFoundException::class)
    fun `SHOULD authenticate throw DefaultNetworkException WHEN LoginRemoteDatasource authenticate is invoked`() {
        coEvery { loginRemoteDatasource.authenticate(any()) } throws HttpException(
            Response.error<AuthenticationResponse>(
                404,
                "".toResponseBody("application/json".toMediaType())
            )
        )

        runBlocking {
            loginRepository.authenticate(LoginParams("email", "123", ""))
        }
    }

    @Test(expected = TypeConversionException::class)
    fun `SHOULD authenticate throw TypeConversionException WHEN LoginLocalDatasource save is invoked`() {
        val expectedResponse = AuthenticationResponse(
            SessionMetadataResponse(
                bearerToken = "",
                expiresIn = 10
            )
        )

        coEvery { loginRemoteDatasource.authenticate(any()) } returns expectedResponse
        coEvery { loginLocalDatasource.save(any()) } throws ClassCastException()

        runBlocking {
            loginRepository.authenticate(LoginParams("email", "123", ""))
        }
    }

}
