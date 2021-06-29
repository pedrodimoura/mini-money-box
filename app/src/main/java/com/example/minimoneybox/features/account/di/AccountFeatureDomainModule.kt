package com.example.minimoneybox.features.account.di

import com.example.minimoneybox.features.account.domain.repository.AccountRepository
import com.example.minimoneybox.features.account.domain.usecase.FetchAccountInformationUseCase
import com.example.minimoneybox.features.account.domain.usecase.QuickAddUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AccountFeatureDomainModule {

    @Provides
    fun providesFetchAccountInformationUseCase(
        accountRepository: AccountRepository,
    ): FetchAccountInformationUseCase = FetchAccountInformationUseCase(accountRepository)

    @Provides
    fun providesQuickAddUseCase(
        accountRepository: AccountRepository,
    ): QuickAddUseCase = QuickAddUseCase(accountRepository)
}
