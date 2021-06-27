package com.example.minimoneybox.common.data.networking.exceptions

class TypeConversionException(cause: Throwable?) :
    Throwable("The type is not the same that you want", cause)
