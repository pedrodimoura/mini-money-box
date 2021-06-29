package com.example.minimoneybox.features.account.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class OneOffPaymentsRequest(
    @SerializedName("Amount")
    val amount: Double,
    @SerializedName("InvestorProductId")
    val investorProductId: Int
)
