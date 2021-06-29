package com.example.minimoneybox.features.account.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductView(
    val id: Int,
    val planValue: Float,
    val moneybox: Float,
    val name: String,
    val category: String,
    val friendlyName: String,
) : Parcelable
