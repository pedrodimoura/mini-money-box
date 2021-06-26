package com.example.minimoneybox.features.account.data.datasource.remote

import com.example.minimoneybox.features.account.data.datasource.remote.model.AccountInformationResponse

interface AccountRemoteDatasource {
    suspend fun fetchAccountInformation(): AccountInformationResponse
}
