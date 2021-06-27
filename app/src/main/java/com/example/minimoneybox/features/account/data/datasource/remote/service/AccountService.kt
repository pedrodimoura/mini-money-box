package com.example.minimoneybox.features.account.data.datasource.remote.service

import com.example.minimoneybox.features.account.data.datasource.remote.model.AccountInformationResponse
import retrofit2.http.GET

const val ACCOUNT_INFORMATION_ENDPOINT = "investorproducts"

interface AccountService {

    @GET(ACCOUNT_INFORMATION_ENDPOINT)
    suspend fun fetchAccountInformation(): AccountInformationResponse
}
