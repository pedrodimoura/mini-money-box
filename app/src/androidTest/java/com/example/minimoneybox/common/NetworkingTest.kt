package com.example.minimoneybox.common

import androidx.test.espresso.IdlingRegistry
import com.example.minimoneybox.common.idling.asAndroidX
import com.jakewharton.espresso.OkHttp3IdlingResource
import javax.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before

private val mockWebServer = MockWebServer()
val testUrl = mockWebServer.url("/").toString()

abstract class NetworkingTest : BaseTest() {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Before
    override fun setup() {
        super.setup()
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create("okhttp", okHttpClient).asAndroidX()
        )
    }

    fun setDispatcher(dispatcher: Dispatcher) {
        mockWebServer.dispatcher = dispatcher
    }
}
