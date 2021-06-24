package com.example.minimoneybox.common.networking.exceptions

class DefaultNetworkError(cause: Throwable?) : Throwable("Something went wrong", cause)
