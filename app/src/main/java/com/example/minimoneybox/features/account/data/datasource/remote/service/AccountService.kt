package com.example.minimoneybox.features.account.data.datasource.remote.service

import com.example.minimoneybox.features.account.data.datasource.remote.model.AccountInformationResponse
import com.example.minimoneybox.features.account.data.datasource.remote.model.OneOffPaymentsRequest
import com.example.minimoneybox.features.account.data.datasource.remote.model.OneOffPaymentsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

const val ACCOUNT_INFORMATION_ENDPOINT = "investorproducts"
const val ONE_OFF_PAYMENTS_ENDPOINT = "oneoffpayments"

interface AccountService {

    @GET(ACCOUNT_INFORMATION_ENDPOINT)
    suspend fun fetchAccountInformation(): AccountInformationResponse

    @POST(ONE_OFF_PAYMENTS_ENDPOINT)
    suspend fun oneOffPayments(
        @Body oneOffPaymentsRequest: OneOffPaymentsRequest
    ): OneOffPaymentsResponse
}
