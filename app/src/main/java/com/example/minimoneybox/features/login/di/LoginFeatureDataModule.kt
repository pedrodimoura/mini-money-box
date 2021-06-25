package com.example.minimoneybox.features.login.di

import com.example.minimoneybox.common.networking.HttpClient
import com.example.minimoneybox.features.login.data.datasource.local.LoginLocalDatasource
import com.example.minimoneybox.features.login.data.datasource.local.impl.LoginLocalDatasourceImpl
import com.example.minimoneybox.features.login.data.datasource.remote.LoginRemoteDatasource
import com.example.minimoneybox.features.login.data.datasource.remote.impl.LoginRemoteDatasourceImpl
import com.example.minimoneybox.features.login.data.repository.LoginRepositoryImpl
import com.example.minimoneybox.features.login.data.service.LoginService
import com.example.minimoneybox.features.login.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginFeatureDataModule {

    @Binds
    abstract fun bindsRepository(
        loginRepositoryImpl: LoginRepositoryImpl,
    ): LoginRepository

    @Binds
    abstract fun bindsLoginRemoteDatasource(
        loginRemoteDatasourceImpl: LoginRemoteDatasourceImpl,
    ): LoginRemoteDatasource

    @Binds
    abstract fun bindsLoginLocalDatasource(
        loginLocalDatasourceImpl: LoginLocalDatasourceImpl,
    ): LoginLocalDatasource

    @Module
    @InstallIn(SingletonComponent::class)
    object LoginServiceModule {
        @Provides
        fun providesLoginService(
            retrofitClient: HttpClient.RetrofitClient,
        ): LoginService = retrofitClient.create(LoginService::class.java)
    }
}
