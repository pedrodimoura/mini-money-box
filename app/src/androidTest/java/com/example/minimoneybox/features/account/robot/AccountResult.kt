package com.example.minimoneybox.features.account.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.minimoneybox.R

class AccountResult {

    fun checkAccountListIsVisible(): AccountResult {
        onView(withId(R.id.recyclerViewAccounts)).check(matches(isDisplayed()))
        return this
    }
}
