package com.example.minimoneybox.features.account.domain.repository

import com.example.minimoneybox.features.account.domain.model.AccountInformation
import com.example.minimoneybox.features.account.domain.model.QuickAddParams
import com.example.minimoneybox.features.account.domain.model.QuickAddResult

interface AccountRepository {
    suspend fun fetchAccountInformation(): AccountInformation
    suspend fun quickAdd(quickAddParams: QuickAddParams): QuickAddResult
}
