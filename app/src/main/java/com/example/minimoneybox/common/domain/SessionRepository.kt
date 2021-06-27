package com.example.minimoneybox.common.domain

import com.example.minimoneybox.common.domain.model.UserCredential

interface SessionRepository {

    fun save(userCredential: UserCredential)

    fun get(): UserCredential

    fun invalidate()

    fun refresh()

}