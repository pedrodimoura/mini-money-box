package com.example.minimoneybox.features.account.data.datasource.remote.impl

import com.example.minimoneybox.features.account.data.datasource.remote.AccountRemoteDatasource
import com.example.minimoneybox.features.account.data.datasource.remote.model.AccountInformationResponse
import com.example.minimoneybox.features.account.data.datasource.remote.service.AccountService
import javax.inject.Inject

class AccountRemoteDatasourceImpl @Inject constructor(
    private val accountService: AccountService,
) : AccountRemoteDatasource {
    override suspend fun fetchAccountInformation(): AccountInformationResponse =
        accountService.fetchAccountInformation()
}
