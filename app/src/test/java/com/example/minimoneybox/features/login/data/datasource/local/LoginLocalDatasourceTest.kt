package com.example.minimoneybox.features.login.data.datasource.local

import com.example.minimoneybox.common.storage.Storage
import com.example.minimoneybox.features.login.data.datasource.local.impl.LoginLocalDatasourceImpl
import com.example.minimoneybox.features.login.domain.model.UserCredential
import com.google.gson.Gson
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class LoginLocalDatasourceTest {

    private val preferences: Storage.Preferences by lazy { mockk() }
    private val gson: Gson by lazy { mockk() }

    private val loginLocalDatasource: LoginLocalDatasource by lazy {
        LoginLocalDatasourceImpl(preferences, gson)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `SHOULD get be successful when Preferences return a valid UserCredential json string`() {

        val expected = UserCredential(
            name = "",
            token = "",
            expiresIn = 0,
            lastActivityTimestamp = 0L
        )

        every { preferences.getString(any(), any()) } returns ""
        every { gson.fromJson(any() as String, UserCredential::class.java) } returns expected

        runBlocking {
            val result = loginLocalDatasource.get()
            assertEquals(expected, result)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `SHOULD get throw an exception when Preferences return null or empty json string`() {
        every { preferences.getString(any(), any()) } returns null

        runBlocking { loginLocalDatasource.get() }
    }

}