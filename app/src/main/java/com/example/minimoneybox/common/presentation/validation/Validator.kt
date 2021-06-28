package com.example.minimoneybox.common.presentation.validation

interface Validator<T> {

    fun validate(t: T): Boolean
}