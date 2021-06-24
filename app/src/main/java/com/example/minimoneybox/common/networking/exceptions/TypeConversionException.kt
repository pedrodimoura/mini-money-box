package com.example.minimoneybox.common.networking.exceptions

class TypeConversionException(cause: Throwable?) :
    Throwable("The type is not the same that you want", cause)
