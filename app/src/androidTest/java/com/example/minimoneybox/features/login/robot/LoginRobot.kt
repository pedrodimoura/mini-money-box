package com.example.minimoneybox.features.login.robot

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.minimoneybox.R
import com.example.minimoneybox.features.login.LoginActivity

class LoginRobot(private val activityScenarioRule: ActivityScenarioRule<LoginActivity>) {

    fun email(email: String): LoginRobot {
        onView(withId(R.id.et_email)).perform(typeText(email))
        return this
    }

    fun password(password: String): LoginRobot {
        onView(withId(R.id.et_password)).perform(typeText(password))
        return this
    }

    fun name(name: String?): LoginRobot {
        onView(withId(R.id.et_name)).perform(typeText(name))
        return this
    }

    fun closeKeyboard(): LoginRobot {
        closeSoftKeyboard()
        return this
    }

    fun authenticate(): LoginResult {
        onView(withId(R.id.btn_sign_in)).perform(click())
        return LoginResult(activityScenarioRule)
    }

}