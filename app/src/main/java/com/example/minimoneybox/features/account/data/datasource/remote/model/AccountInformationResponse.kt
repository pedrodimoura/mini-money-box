package com.example.minimoneybox.features.account.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class AccountInformationResponse(
    @SerializedName("TotalPlanValue")
    val totalPlanValue: Float,
    @SerializedName("TotalEarnings")
    val totalEarnings: Float,
    @SerializedName("TotalContributionsNet")
    val totalContributionsNet: Float,
    @SerializedName("TotalEarningsAsPercentage")
    val totalEarningsAsPercentage: Double,
    @SerializedName("Accounts")
    val accounts: List<AccountResponse>
)
