package com.example.minimoneybox.features.account.domain.usecase

import com.example.minimoneybox.features.account.domain.model.QuickAddParams
import com.example.minimoneybox.features.account.domain.model.QuickAddResult
import com.example.minimoneybox.features.account.domain.repository.AccountRepository
import javax.inject.Inject

class QuickAddUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(quickAddParams: QuickAddParams): QuickAddResult =
        accountRepository.quickAdd(quickAddParams)
}
