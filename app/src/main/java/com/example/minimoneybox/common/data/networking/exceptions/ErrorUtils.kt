package com.example.minimoneybox.common.data.networking.exceptions

import java.net.HttpURLConnection
import retrofit2.HttpException

fun Throwable.localThrowableToDomain() = when (this) {
    is ClassCastException -> TypeConversionException(this)
    else -> DefaultLocalException(this)
}

fun Throwable.remoteThrowableToDomain() = when (this) {
    is HttpException -> parseHttpException()
    else -> DefaultNetworkError(this)
}

fun HttpException.parseHttpException() = when (this.code()) {
    HttpURLConnection.HTTP_NOT_FOUND -> ResourceNotFoundException(this)
    else -> DefaultNetworkError(this)
}