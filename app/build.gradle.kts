@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.abiao.android.application)
    alias(libs.plugins.androidx.hilt)
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "com.abiao.composedemo"
}

dependencies {
    implementation(libs.androidx.hilt)
    ksp(libs.androidx.hilt.compiler)
    implementation(project(":lib_nowinandroid"))

    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}