package com.example.minimoneybox.common.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import java.io.FileNotFoundException
import java.net.URL
import org.junit.Rule

abstract class BaseUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Throws(FileNotFoundException::class)
    fun readFile(path: String): String {
        val content: URL? = ClassLoader.getSystemResource(path)
        return content?.readText() ?: throw FileNotFoundException("File path: $path was not found")
    }
}
