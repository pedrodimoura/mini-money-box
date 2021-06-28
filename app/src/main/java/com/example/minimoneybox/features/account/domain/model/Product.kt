package com.example.minimoneybox.features.account.domain.model

data class Product(
    val id: String,
    val planValue: Float,
    val moneybox: Float,
    val productDetail: ProductDetails,
) {
    data class ProductDetails(
        val name: String,
        val category: String,
        val friendlyName: String,
    )
}
