package com.example.minimoneybox.features.account.domain.repository

import com.example.minimoneybox.features.account.domain.model.AccountInformation

interface AccountRepository {
    suspend fun fetchAccountInformation(): AccountInformation
}
