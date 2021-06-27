package com.example.minimoneybox.common.data.networking.exceptions

class ResourceNotFoundException(
    cause: Throwable?
) : Throwable("Remote resource not found", cause)
