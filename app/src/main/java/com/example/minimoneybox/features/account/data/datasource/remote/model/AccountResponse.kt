package com.example.minimoneybox.features.account.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("Type")
    val type: TypeResponse,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Wrapper")
    val wrapper: Wrapper
) {
    enum class TypeResponse(name: String) {
        @SerializedName("Isa")
        ISA("Isa"),

        @SerializedName("Lisa")
        LISA("Lisa"),

        @SerializedName("Gia")
        GIA("Gia"),
    }

    data class Wrapper(
        @SerializedName("TotalValue")
        val totalValue: Float,
        @SerializedName("TotalContributions")
        val totalContributions: Float,
        @SerializedName("EarningsNet")
        val earningsNet: Float,
        @SerializedName("EarningsAsPercentage")
        val earningsAsPercentage: Double,
    )
}
