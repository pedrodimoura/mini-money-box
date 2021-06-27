package com.example.minimoneybox.common.data.networking.exceptions

import java.io.IOException

fun <T> Result<T>.ifThrowParseError(): T = this.getOrElse {
    when (it) {
        is IOException -> throw SessionExpiredException(it)
        else -> throw DefaultNetworkError(it)
    }
}