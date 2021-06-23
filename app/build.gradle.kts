plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

repositories {
    google()
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")
    defaultConfig {
        applicationId("com.example.minimoneybox")
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner("android.support.test.runner.AndroidJUnitRunner")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Libs.kotlinStdLib)

    implementation(Libs.coreKtx)
    implementation(Libs.appCompat)

    implementation(Libs.fragmentKtx)
    implementation(Libs.material)
    implementation(Libs.constraintLayout)
    implementation(Libs.securityCrypto)

    implementation(Libs.lifecycleViewModelKtx)
    implementation(Libs.lifecycleLiveDataKtx)

    implementation(Libs.coroutinesAndroid)

    implementation(Libs.retrofit)

    implementation(Libs.okHttp)
    implementation(Libs.loggingInterceptor)
    testImplementation(TestLibs.mockWebServer)

    implementation(Libs.gson)
    implementation(Libs.gsonRetrofitConverter)

    // Dagger Hilt
    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltCompiler)
    androidTestImplementation(TestLibs.hiltAndroidTesting)
    kaptAndroidTest(TestLibs.hiltCompiler)
    testImplementation(TestLibs.hiltAndroidTesting)
    kaptTest(TestLibs.hiltCompiler)

    // Image and drawable libs
    implementation(Libs.lottie)

    implementation(Libs.navigationFragmentKtx)
    implementation(Libs.navigationUiKtx)

    // Check if is necessary
    debugImplementation(TestLibs.fragmentTesting)

    testImplementation(TestLibs.mockk)
    androidTestImplementation(TestLibs.mockkAndroid)

    testImplementation(TestLibs.junit)
    androidTestImplementation(TestLibs.extJunit)
    androidTestImplementation(TestLibs.espressoCore)
}
