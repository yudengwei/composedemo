@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.abiao.android.application)
    alias(libs.plugins.abiao.hilt)
}

android {
    namespace = "com.abiao.composedemo"
}

dependencies {
    implementation(projects.libNowinandroid)

    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
