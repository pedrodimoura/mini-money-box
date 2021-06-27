package com.example.minimoneybox.features.account.presentation

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.minimoneybox.common.NetworkingTest
import com.example.minimoneybox.common.di.NetworkHiltModule
import com.example.minimoneybox.features.account.data.datasource.remote.service.ACCOUNT_INFORMATION_ENDPOINT
import com.example.minimoneybox.features.account.presentation.activity.AccountActivity
import com.example.minimoneybox.features.account.robot.AccountResult
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import java.net.HttpURLConnection
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val TAG = "AccountActivityTest"
private const val DEFAULT_NAME_ARGUMENT_KEY = "name"
private const val DEFAULT_NAME_ARGUMENT = "Pedro Moura"

@UninstallModules(NetworkHiltModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AccountActivityTest : NetworkingTest() {

    private lateinit var activityScenario: ActivityScenario<AccountActivity>

    private val accountDispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val url = request.path.toString()
            return when {
                url.contains(ACCOUNT_INFORMATION_ENDPOINT) -> MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(readStringFile("responses/account_information_response.json"))
                else -> MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
            }
        }
    }

    @Before
    override fun setup() {
        super.setup()
        super.setDispatcher(accountDispatcher)
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }

    @Test
    fun shouldShowGreetingsMessageWithName() {
        activityScenario = launchActivity(getDefaultIntent(DEFAULT_NAME_ARGUMENT))

        AccountResult()
            .checkGreetingsWithNameIsDisplayed(DEFAULT_NAME_ARGUMENT)
            .checkAccountListIsVisible()
    }

    @Test
    fun shouldShowGreetingsMessageWithoutName() {
        activityScenario = launchActivity(getDefaultIntent(""))

        AccountResult()
            .checkGreetingsWithoutNameIsDisplayed()
            .checkAccountListIsVisible()
    }

    @Test
    fun shouldShowAccountsList() {
        activityScenario = launchActivity(getDefaultIntent())

        AccountResult()
            .checkAccountListIsVisible()
    }

    private fun getDefaultIntent(nameArg: String? = null): Intent =
        Intent(ApplicationProvider.getApplicationContext(), AccountActivity::class.java).apply {
            putExtra(DEFAULT_NAME_ARGUMENT_KEY, nameArg ?: DEFAULT_NAME_ARGUMENT)
        }

}
