plugins {
    alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.abiao.hilt)
    id("kotlinx-serialization")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.abiao.network"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.libNowinandroid.model)
    implementation(projects.libCommon)
    implementation(projects.libUtil)

    testImplementation(libs.kotlinx.coroutines.test)
}