plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.waautohang"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.waautohang"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // so timer alarms can fire exactly
        setProperty("archivesBaseName", "waautohang")
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity-ktx:1.9.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}
