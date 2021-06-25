package com.example.minimoneybox.common.di

import com.example.minimoneybox.common.storage.Storage
import com.example.minimoneybox.common.storage.preferences.PreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageHiltModule {

    @Binds
    abstract fun bindsStoragePreferences(
        preferencesImpl: PreferencesImpl,
    ): Storage.Preferences
}
