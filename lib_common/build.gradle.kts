@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.abiao.android.compose)
    alias(libs.plugins.abiao.hilt)
}

android {
    namespace = "com.abiao.common"
}

dependencies {
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

    implementation(libs.androidx.startup.runtime)

    implementation(projects.libUtil)
}