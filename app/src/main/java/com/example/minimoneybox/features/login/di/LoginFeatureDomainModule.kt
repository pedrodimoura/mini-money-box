package com.example.minimoneybox.features.login.di

import com.example.minimoneybox.features.login.domain.repository.LoginRepository
import com.example.minimoneybox.features.login.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object LoginFeatureDomainModule {

    @Provides
    fun providesLoginUseCase(
        loginRepository: LoginRepository
    ) = LoginUseCase(loginRepository)
}
