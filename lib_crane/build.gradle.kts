@file:Suppress("UnstableApiUsage")

plugins {
    //alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.abiao.android.application)
    alias(libs.plugins.abiao.android.compose)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.androidx.hilt)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.abiao.crane"
}

dependencies {
    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.lifecycle.viewModelCompose)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.hilt)
    ksp(libs.androidx.hilt.compiler)

    implementation(libs.coil.compose)

    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

    implementation(project(":lib_util"))
    implementation(project(":lib_common"))
}