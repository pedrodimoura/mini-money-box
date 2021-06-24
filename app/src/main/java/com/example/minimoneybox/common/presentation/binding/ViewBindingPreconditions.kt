package com.example.minimoneybox.common.presentation.binding

import android.os.Build
import android.os.Looper

internal fun isMainThread(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        Looper.getMainLooper().isCurrentThread
    else (Looper.myLooper() == Looper.getMainLooper())
}

internal fun checkMainThread(lazyMessage: String? = null) {
    if (isMainThread().not()) {
        val message = lazyMessage ?: "This call must be executed on main thread"
        throw IllegalStateException(message)
    }
}