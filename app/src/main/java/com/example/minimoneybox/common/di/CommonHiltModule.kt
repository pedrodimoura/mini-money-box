package com.example.minimoneybox.common.di

import com.example.minimoneybox.common.data.repository.SessionRepositoryImpl
import com.example.minimoneybox.common.domain.SessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonHiltModule {

    @Binds
    abstract fun bindsSessionRepository(
        sessionRepositoryImpl: SessionRepositoryImpl,
    ): SessionRepository
}
