package com.example.minimoneybox.features.account.data.datasource.remote.service

import com.example.minimoneybox.features.account.data.datasource.remote.model.AccountInformationResponse
import retrofit2.http.GET

const val INVESTOR_PRODUCTS_ENDPOINT = "investorproducts"

interface AccountService {

    @GET(INVESTOR_PRODUCTS_ENDPOINT)
    suspend fun fetchAccountInformation(): AccountInformationResponse
}
