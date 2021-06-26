package com.example.minimoneybox.common.test

import java.io.FileNotFoundException
import java.net.URL

abstract class BaseUnitTest {
    @Throws(FileNotFoundException::class)
    fun readFile(path: String): String {
        val content: URL? = ClassLoader.getSystemResource(path)
        return content?.readText() ?: throw FileNotFoundException("File path: $path was not found")
    }
}
