package com.example.minimoneybox.features.login.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.minimoneybox.R

class LoginResult {

    fun accountScreenIsShowing(): LoginResult {
        onView(withId(R.id.accountScreenRoot)).check(matches(isDisplayed()))
        return this
    }
}
