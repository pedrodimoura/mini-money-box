package com.example.minimoneybox.features.login.robot

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.minimoneybox.R

class LoginRobot {

    fun typeEmail(email: String): LoginRobot {
        onView(withId(R.id.et_email)).perform(typeText(email))
        return this
    }

    fun typePassword(password: String): LoginRobot {
        onView(withId(R.id.et_password)).perform(typeText(password))
        return this
    }

    fun typeName(name: String): LoginRobot {
        onView(withId(R.id.et_name)).perform(typeText(name))
        return this
    }

    fun closeSoftKeyboard(): LoginRobot {
        Espresso.closeSoftKeyboard()
        return this
    }

    fun authenticate(): LoginResult {
        onView(withId(R.id.btn_sign_in)).perform(click())
        return LoginResult()
    }
}
