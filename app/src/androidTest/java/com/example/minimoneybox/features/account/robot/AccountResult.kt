package com.example.minimoneybox.features.account.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.minimoneybox.R

class AccountResult {

    fun checkGreetingsWithNameIsDisplayed(name: String): AccountResult {
        onView(withText("Hello,")).check(matches(isDisplayed()))
        onView(withText("$name!")).check(matches(isDisplayed()))
        return this
    }

    fun checkGreetingsWithoutNameIsDisplayed(): AccountResult {
        onView(withText("Hello,")).check(doesNotExist())
        onView(withText("Hello!")).check(matches(isDisplayed()))
        return this
    }

    fun checkAccountListIsVisible(): AccountResult {
        onView(withId(R.id.recyclerViewAccounts)).check(matches(isDisplayed()))
        return this
    }
}
