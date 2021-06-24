package com.example.minimoneybox.common.networking.exceptions

class ResourceNotFoundException(
    cause: Throwable?
) : Throwable("Remote resource not found", cause)
