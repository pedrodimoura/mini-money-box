package com.example.minimoneybox.common.data.repository

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.minimoneybox.common.data.storage.Storage
import com.example.minimoneybox.common.domain.SessionRepository
import com.example.minimoneybox.common.domain.model.UserCredential
import com.example.minimoneybox.common.workmanager.SESSION_LOGOUT_WORKER_NAME
import com.example.minimoneybox.common.workmanager.SessionLogoutWorker
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val USER_CREDENTIAL = "user-credential"
private const val IS_REFRESH_ENABLED = "is-refresh-enabled"
private const val SESSION_EXPIRATION = 5L

class SessionRepositoryImpl @Inject constructor(
    private val preferences: Storage.Preferences,
    private val gson: Gson,
    @ApplicationContext private val context: Context,
) : SessionRepository {

    override fun save(userCredential: UserCredential) {
        preferences.saveString(USER_CREDENTIAL, gson.toJson(userCredential))
        preferences.saveBoolean(IS_REFRESH_ENABLED, true)
        refresh()
    }

    override fun get(): UserCredential {
        val userCredentialJson =
            preferences.getString(USER_CREDENTIAL) ?: throw IOException("Credential not found")
        return gson.fromJson(userCredentialJson, UserCredential::class.java)
    }

    override fun invalidate() {
        preferences.invalidate(USER_CREDENTIAL)
        preferences.invalidate(IS_REFRESH_ENABLED)
    }

    override fun refresh() {
        if (isRefreshEnabled()) {
            val workRequest = OneTimeWorkRequestBuilder<SessionLogoutWorker>()
                .setInitialDelay(SESSION_EXPIRATION, TimeUnit.MINUTES)
                .addTag(SESSION_LOGOUT_WORKER_NAME)
                .build()
            WorkManager.getInstance(context).enqueueUniqueWork(
                SESSION_LOGOUT_WORKER_NAME,
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
        }
    }

    private fun isRefreshEnabled(): Boolean {
        return preferences.getBoolean(IS_REFRESH_ENABLED, false)
    }
}

