package com.example.minimoneybox.common.di

import com.example.minimoneybox.common.data.storage.Storage
import com.example.minimoneybox.common.data.storage.preferences.PreferencesImpl
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
