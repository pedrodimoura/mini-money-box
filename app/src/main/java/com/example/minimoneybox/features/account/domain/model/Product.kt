package com.example.minimoneybox.features.account.domain.model

data class Product(
    val id: Int,
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
