package com.example.minimoneybox.common

import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.work.Configuration
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
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
        initializeWorkManagerForInstrumentationTests()
    }

    fun readStringFile(path: String): String {
        val assetManager = applicationContext.assets
        val inputStream = assetManager.open(path)
        val readerBuffer = ByteArray(inputStream.available())

        inputStream.read(readerBuffer)
        inputStream.close()

        return String(readerBuffer)
    }

    private fun initializeWorkManagerForInstrumentationTests() {
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(applicationContext, config)
    }
}
