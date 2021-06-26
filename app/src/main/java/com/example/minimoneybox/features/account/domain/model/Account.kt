package com.example.minimoneybox.features.account.domain.model

data class Account(
    val type: Type,
    val name: String,
    val totalValue: Float,
    val totalContributions: Float,
    val earningsNet: Float,
    val earningsAsPercentage: Double,
) {
    enum class Type(name: String) {
        ISA("Isa"),
        LISA("Lisa"),
        GIA("Gia"),
    }
}
