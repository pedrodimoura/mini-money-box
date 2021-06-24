package com.example.minimoneybox.features.login.robot

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.airbnb.lottie.LottieAnimationView
import com.example.minimoneybox.R
import com.example.minimoneybox.features.login.LoginActivity
import org.junit.Assert.assertEquals

class LoginResult(private val activityScenarioRule: ActivityScenarioRule<LoginActivity>) {

    fun animationWasStarted(): LoginResult {
        onView(withId(R.id.animation)).check { view, noViewFoundException ->
            ((noViewFoundException == null).and((view as LottieAnimationView).isAnimating))
        }
        return this
    }

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    fun isSuccess(): LoginResult {

        activityScenarioRule.scenario.onActivity {
            navController.setGraph(R.navigation.login_nav_graph)

            Navigation.setViewNavController(
                it.requireViewById(R.id.activityMainRoot),
                navController
            )
        }

        assertEquals(R.id.activityAccounts, navController.currentDestination?.id)
        return this
    }
}