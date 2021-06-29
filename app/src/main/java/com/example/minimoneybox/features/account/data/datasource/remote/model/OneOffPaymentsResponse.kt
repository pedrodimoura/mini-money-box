package com.example.minimoneybox.features.account.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class OneOffPaymentsResponse(
    @SerializedName("Moneybox")
    val moneybox: Double
)
