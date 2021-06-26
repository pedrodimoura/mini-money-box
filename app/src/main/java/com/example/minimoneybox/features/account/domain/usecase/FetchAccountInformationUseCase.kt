package com.example.minimoneybox.features.account.domain.usecase

import com.example.minimoneybox.features.account.domain.model.AccountInformation
import com.example.minimoneybox.features.account.domain.repository.AccountRepository
import javax.inject.Inject

class FetchAccountInformationUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {

    suspend operator fun invoke(): AccountInformation =
        accountRepository.fetchAccountInformation()
}
