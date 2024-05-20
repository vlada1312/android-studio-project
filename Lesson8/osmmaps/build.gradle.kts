plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "ru.mirea.sosnovskayave.osmmaps"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.mirea.sosnovskayave.osmmaps"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

    implementation("org.osmdroid:osmdroid-android:6.1.16")
    // библиотека для хранения данных SharedPreferences
    implementation("androidx.preference:preference:1.2.0")


    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}