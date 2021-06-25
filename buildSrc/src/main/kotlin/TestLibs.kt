object TestLibs {
    const val junit = "junit:junit:${Version.junit}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Version.okHttp}"
    const val extJunit = "androidx.test.ext:junit:${Version.extJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Version.espresso}"
    const val espressoIntent = "androidx.test.espresso:espresso-intents:${Version.espresso}"

    const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:${Version.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Version.hilt}"

    const val fragmentTesting = "androidx.fragment:fragment-testing:${Version.fragmentKtx}"

    const val mockk = "io.mockk:mockk:${Version.mockk}"
    const val mockkAndroid = "io.mockk:mockk-android:${Version.mockk}"
    const val dexMaker = "com.linkedin.dexmaker:dexmaker:${Version.dexMaker}"

    const val coreTesting = "androidx.arch.core:core-testing:${Version.coreTesting}"
    const val coreKtxTesting = "androidx.test:core-ktx:${Version.coreKtxTest}"

    const val navigation = "androidx.navigation:navigation-testing:${Version.navigation}"
    const val okHttpIdlingResource =
        "com.jakewharton.espresso:okhttp3-idling-resource:${Version.okHttpIdlingResource}"
}