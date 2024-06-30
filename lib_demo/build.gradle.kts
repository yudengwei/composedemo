@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.abiao.android.application)
    alias(libs.plugins.abiao.android.compose)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.androidx.hilt)
}

android {
    namespace = "com.abiao.demo"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.preview)
    implementation(libs.androidx.compose.preview.android)
    implementation(libs.androidx.compose.materialWindow)
    debugImplementation(libs.androidx.compose.tooling)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

    implementation(project(":lib_util"))
    implementation(project(":lib_common"))

    implementation(libs.androidx.hilt)
    ksp(libs.androidx.hilt.compiler)
}