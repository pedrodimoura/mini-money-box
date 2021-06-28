package com.example.minimoneybox.common.di

import com.example.minimoneybox.common.data.repository.SessionRepositoryImpl
import com.example.minimoneybox.common.domain.SessionRepository
import com.example.minimoneybox.common.presentation.validation.EmailValidator
import com.example.minimoneybox.common.presentation.validation.Validator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class EmailValidatorType

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonHiltModule {

    @Binds
    abstract fun bindsSessionRepository(
        sessionRepositoryImpl: SessionRepositoryImpl,
    ): SessionRepository

    @EmailValidatorType
    @Binds
    abstract fun bindsEmailValidator(
        emailValidator: EmailValidator
    ): Validator<String>
}
