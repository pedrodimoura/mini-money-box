package com.example.minimoneybox.features.login.presentation

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.minimoneybox.features.login.LoginActivity
import com.example.minimoneybox.features.login.robot.LoginRobot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    // Inject here fake modules

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityScenario = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun shouldShowAccountsActivity() {
        val loginRobot = LoginRobot(activityScenario)

        loginRobot.email("email@email.com")
        loginRobot.password("1234")
        loginRobot.name("Pedro Moura")
        loginRobot.closeKeyboard()

        val loginResult = loginRobot.authenticate()

        loginResult.animationWasStarted()
        loginResult.isSuccess()
    }
}