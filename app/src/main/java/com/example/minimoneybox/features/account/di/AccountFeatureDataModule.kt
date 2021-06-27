package com.example.minimoneybox.features.account.di

import com.example.minimoneybox.common.data.networking.HttpClient
import com.example.minimoneybox.features.account.data.datasource.remote.AccountRemoteDatasource
import com.example.minimoneybox.features.account.data.datasource.remote.impl.AccountRemoteDatasourceImpl
import com.example.minimoneybox.features.account.data.datasource.remote.service.AccountService
import com.example.minimoneybox.features.account.data.repository.AccountRepositoryImpl
import com.example.minimoneybox.features.account.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AccountFeatureDataModule {

    @Binds
    abstract fun bindsAccountRemoteDatasource(
        accountRemoteDatasourceImpl: AccountRemoteDatasourceImpl,
    ): AccountRemoteDatasource

    @Binds
    abstract fun bindsAccountRepository(
        accountRepositoryImpl: AccountRepositoryImpl,
    ): AccountRepository

    @Module
    @InstallIn(SingletonComponent::class)
    object AccountServiceModule {
        @Provides
        fun providesAccountService(
            retrofitClient: HttpClient.RetrofitClient
        ): AccountService = retrofitClient.create(AccountService::class.java)
    }
}
