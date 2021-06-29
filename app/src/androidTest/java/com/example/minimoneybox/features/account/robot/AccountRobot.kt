package com.example.minimoneybox.features.account.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.minimoneybox.R
import com.example.minimoneybox.features.account.presentation.adapter.AccountRecyclerViewAdapter.AccountRecyclerViewHolder as ProductViewHolder

class AccountRobot {

    fun clickOnFirstProduct(): AccountResult {
        onView(withId(R.id.recyclerViewAccounts)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ProductViewHolder>(0, click())
        )
        return AccountResult()
    }
}
