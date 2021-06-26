package com.example.minimoneybox.common.test

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

abstract class MockWebServerTest : BaseUnitTest() {

    val mockWebServer: MockWebServer by lazy { MockWebServer() }

    @Before
    open fun setup() {
        mockWebServer.start()
    }

    @After
    open fun tearDown() {
        mockWebServer.shutdown()
    }
}
