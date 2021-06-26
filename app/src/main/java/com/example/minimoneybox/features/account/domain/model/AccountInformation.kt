package com.example.minimoneybox.features.account.domain.model

data class AccountInformation(
    val totalPlanValue: Float,
    val totalEarnings: Float,
    val totalContributionsNet: Float,
    val totalEarningsAsPercentage: Double,
    val accounts: List<Account>
)
