plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

repositories {
    google()
    mavenCentral()
}

android {
    compileSdkVersion(AppConfiguration.compileVersion)
    buildToolsVersion(AppConfiguration.buildTools)
    defaultConfig {
        applicationId(AppConfiguration.appId)
        minSdkVersion(AppConfiguration.minSdk)
        targetSdkVersion(AppConfiguration.targetSdk)
        versionCode = AppConfiguration.versionCode
        versionName = AppConfiguration.versionName
        testInstrumentationRunner(AppConfiguration.instrumentationRunner)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://api-test01.moneyboxapp.com/\"")
            buildConfigField("String", "APP_ID", "\"3a97b932a9d449c981b595\"")
            buildConfigField("String", "API_VERSION", "\"3.0.0\"")
        }
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            buildConfigField("String", "BASE_URL", "\"https://api-test01.moneyboxapp.com/\"")
            buildConfigField("String", "APP_ID", "\"3a97b932a9d449c981b595\"")
            buildConfigField("String", "API_VERSION", "\"3.0.0\"")
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
    implementation(Libs.hiltWork)
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
    androidTestImplementation(TestLibs.workManagerTesting)
}


