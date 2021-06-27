package com.example.minimoneybox.common.workmanager

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.minimoneybox.common.data.repository.SessionRepositoryImpl
import com.example.minimoneybox.common.data.storage.preferences.PreferencesImpl
import com.example.minimoneybox.common.domain.SessionRepository
import com.google.gson.Gson

const val SESSION_LOGOUT_WORKER_NAME = "session-logout-worker"

class SessionLogoutWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
) : Worker(context, workerParameters) {

    private val sessionRepository: SessionRepository by lazy {
        SessionRepositoryImpl(PreferencesImpl(context), Gson(), context)
    }

    override fun doWork(): Result {
        sessionRepository.invalidate()
        return Result.success()
    }
}