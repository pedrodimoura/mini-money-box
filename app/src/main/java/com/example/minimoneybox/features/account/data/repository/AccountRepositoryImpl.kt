package com.example.minimoneybox.features.account.data.repository

import com.example.minimoneybox.common.data.networking.exceptions.ifThrowParseError
import com.example.minimoneybox.features.account.data.datasource.remote.AccountRemoteDatasource
import com.example.minimoneybox.features.account.data.datasource.remote.model.AccountInformationResponse
import com.example.minimoneybox.features.account.data.datasource.remote.model.AccountResponse
import com.example.minimoneybox.features.account.data.datasource.remote.model.ProductResponse
import com.example.minimoneybox.features.account.domain.model.Account
import com.example.minimoneybox.features.account.domain.model.AccountInformation
import com.example.minimoneybox.features.account.domain.model.Product
import com.example.minimoneybox.features.account.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountRemoteDatasource: AccountRemoteDatasource,
) : AccountRepository {
    override suspend fun fetchAccountInformation(): AccountInformation {
        return runCatching {
            accountRemoteDatasource.fetchAccountInformation().asDomain()
        }.ifThrowParseError()
    }
}

fun AccountInformationResponse.asDomain(): AccountInformation = AccountInformation(
    this.totalPlanValue,
    this.totalEarnings,
    totalContributionsNet,
    totalEarningsAsPercentage,
    accounts.map { accountResponse -> accountResponse.asDomain() },
    products.map { productResponse -> productResponse.asDomain() },
)

internal fun AccountResponse.asDomain(): Account = Account(
    Account.Type.valueOf(type.name),
    name,
    wrapper.totalValue,
    wrapper.totalContributions,
    wrapper.earningsNet,
    wrapper.earningsAsPercentage,
)

internal fun ProductResponse.asDomain(): Product = Product(
    this.id,
    this.planValue,
    this.moneybox,
    this.productDetail.asDomain(),
)

internal fun ProductResponse.ProductDetailResponse.asDomain() =
    Product.ProductDetails(
        this.name,
        this.category,
        this.friendlyName,
    )
