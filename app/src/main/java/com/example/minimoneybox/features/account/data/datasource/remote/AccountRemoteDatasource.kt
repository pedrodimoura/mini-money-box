package com.example.minimoneybox.features.account.data.datasource.remote

import com.example.minimoneybox.features.account.data.datasource.remote.model.AccountInformationResponse
import com.example.minimoneybox.features.account.data.datasource.remote.model.OneOffPaymentsRequest
import com.example.minimoneybox.features.account.data.datasource.remote.model.OneOffPaymentsResponse

interface AccountRemoteDatasource {
    suspend fun fetchAccountInformation(): AccountInformationResponse
    suspend fun oneOffPayments(
        oneOffPaymentsRequest: OneOffPaymentsRequest
    ): OneOffPaymentsResponse
}
