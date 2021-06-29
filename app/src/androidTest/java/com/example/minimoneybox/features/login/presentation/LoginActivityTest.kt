package com.example.minimoneybox.features.login.presentation

import androidx.navigation.Navigation
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.minimoneybox.R
import com.example.minimoneybox.common.NetworkingTest
import com.example.minimoneybox.common.di.NetworkHiltModule
import com.example.minimoneybox.features.account.data.datasource.remote.service.ACCOUNT_INFORMATION_ENDPOINT
import com.example.minimoneybox.features.login.data.datasource.remote.service.USERS_LOGIN_ENDPOINT
import com.example.minimoneybox.features.login.presentation.activity.LoginActivity
import com.example.minimoneybox.features.login.robot.LoginRobot
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

@UninstallModules(NetworkHiltModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest : NetworkingTest() {

    private lateinit var loginActivityScenario: ActivityScenario<LoginActivity>

    private val loginDispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val url = request.path.toString()
            return when {
                url.contains(USERS_LOGIN_ENDPOINT) -> MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(readStringFile("responses/authentication_response.json"))
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
        super.setDispatcher(loginDispatcher)
        loginActivityScenario = launchActivity()
        loginActivityScenario.onActivity {
            navController.setGraph(R.navigation.login_nav_graph)

            Navigation.setViewNavController(
                it.requireViewById(R.id.activityMainRoot),
                navController
            )
        }
    }

    @After
    fun tearDown() {
        loginActivityScenario.close()
    }

    @Test
    fun shouldLoginSucceedAndNavigateToAccountScreen() {
        LoginRobot()
            .typeEmail("email@email.com")
            .typePassword("1234")
            .typeName("Pedro Moura")
            .closeSoftKeyboard()
            .authenticate()
            .accountScreenIsShowing()
    }
}
