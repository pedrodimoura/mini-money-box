package com.example.minimoneybox.common.idling

import androidx.test.espresso.IdlingResource

internal fun IdlingResource.ResourceCallback.asSupport() = SupportResourceCallback(this)

internal fun android.support.test.espresso.IdlingResource.asAndroidX() =
    AndroidXIdlingResource(this)
