package com.example.minimoneybox.features.account.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("Id")
    val id: String,
    @SerializedName("PlanValue")
    val planValue: Float,
    @SerializedName("Moneybox")
    val moneybox: Float,
    @SerializedName("Product")
    val productDetail: ProductDetailResponse,
) {
    data class ProductDetailResponse(
        @SerializedName("Name")
        val name: String,
        @SerializedName("CategoryType")
        val category: String,
        @SerializedName("FriendlyName")
        val friendlyName: String,
    )
}
