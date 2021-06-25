plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

repositories {
    google()
    mavenCentral()
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
        testInstrumentationRunner("com.example.minimoneybox.common.HiltAndroidRunner")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://www.google.com.br/\"")
            buildConfigField("String", "APP_ID_HEADER", "\"AppId\"")
            buildConfigField("String", "APP_ID", "\"3a97b932a9d449c981b595\"")
        }
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            buildConfigField("String", "BASE_URL", "\"https://www.google.com/\"")
            buildConfigField("String", "APP_ID_HEADER", "\"AppId\"")
            buildConfigField("String", "APP_ID", "\"3a97b932a9d449c981b595\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }

    hilt {
        enableTransformForLocalTests = true
    }

    sourceSets {
        getByName("androidTest").assets.srcDirs("src/debug/assets")
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
    implementation(Libs.workManager)

    implementation(Libs.lifecycleViewModelKtx)
    implementation(Libs.lifecycleLiveDataKtx)

    implementation(Libs.coroutinesAndroid)

    implementation(Libs.retrofit)

    implementation(Libs.okHttp)
    implementation(Libs.loggingInterceptor)

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
    androidTestImplementation(TestLibs.navigation)

    // Check if is necessary
    debugImplementation(TestLibs.fragmentTesting)

    testImplementation(TestLibs.mockk)
    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.coreTesting)
    testImplementation(TestLibs.mockWebServer)


    androidTestImplementation(TestLibs.extJunit)
    androidTestImplementation(TestLibs.espressoCore)
    androidTestImplementation(TestLibs.espressoIntent)
    androidTestImplementation(TestLibs.mockWebServer)
    androidTestImplementation(TestLibs.coreKtxTesting)
    androidTestImplementation(TestLibs.okHttpIdlingResource)
    androidTestImplementation(TestLibs.mockkAndroid)
    androidTestImplementation(TestLibs.dexMaker)
}
