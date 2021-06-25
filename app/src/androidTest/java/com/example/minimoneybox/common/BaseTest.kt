package com.example.minimoneybox.common

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule

abstract class BaseTest {

    val navController: NavController by lazy {
        TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    @get:Rule
    val hilAndroidRule: HiltAndroidRule = HiltAndroidRule(this)

    val applicationContext: Context by lazy { ApplicationProvider.getApplicationContext() }

    @Before
    open fun setup() {
        hilAndroidRule.inject()
    }

    fun readStringFile(path: String): String {
        val assetManager = applicationContext.assets
        val inputStream = assetManager.open(path)
        val readerBuffer = ByteArray(inputStream.available())

        inputStream.read(readerBuffer)
        inputStream.close()

        return String(readerBuffer)
    }
}
