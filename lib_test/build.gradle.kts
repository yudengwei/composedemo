@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.abiao.android.compose)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.abiao.test"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

    implementation(libs.coil3.compose)
    implementation(libs.glide.compose)

    implementation(project(":lib_util"))
    implementation(project(":lib_common"))
    implementation(project(":lib_imagepick"))
}