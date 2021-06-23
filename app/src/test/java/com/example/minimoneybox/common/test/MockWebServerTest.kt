package com.example.minimoneybox.common.test

import java.io.FileNotFoundException
import java.net.URL
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

open class MockWebServerTest {

    val mockWebServer: MockWebServer by lazy { MockWebServer() }

    @Throws(FileNotFoundException::class)
    fun readFile(path: String): String {
        val content: URL? = ClassLoader.getSystemResource(path)
        return content?.readText() ?: throw FileNotFoundException("File path: $path was not found")
    }

    @Before
    open fun setup() {
        mockWebServer.start()
    }

    @After
    open fun tearDown() {
        mockWebServer.shutdown()
    }
}
