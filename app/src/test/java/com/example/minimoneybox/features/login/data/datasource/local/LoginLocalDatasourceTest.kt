package com.example.minimoneybox.features.login.data.datasource.local

import com.example.minimoneybox.common.domain.SessionRepository
import com.example.minimoneybox.common.domain.model.UserCredential
import com.example.minimoneybox.features.login.data.datasource.local.impl.LoginLocalDatasourceImpl
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class LoginLocalDatasourceTest {

    private val sessionRepository: SessionRepository by lazy { mockk() }

    private val loginLocalDatasource: LoginLocalDatasource by lazy {
        LoginLocalDatasourceImpl(sessionRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `SHOULD get be successful when SessionRepository return a valid UserCredential`() {

        val expected = UserCredential(
            name = "",
            token = "",
            expiresIn = 0,
            lastActivityTimestamp = 0L
        )

        every { sessionRepository.get() } returns expected

        runBlocking {
            val result = loginLocalDatasource.get()
            assertEquals(expected, result)
        }
    }

    @Test(expected = IOException::class)
    fun `SHOULD get throw an exception when SessionRepository return an invalid UserCredential`() {
        every { sessionRepository.get() } throws IOException()

        runBlocking { loginLocalDatasource.get() }
    }

}