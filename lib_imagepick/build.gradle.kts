@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.abiao.android.compose)
}

android {
    namespace = "com.imagepick"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)

    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

    compileOnly(libs.coil3.compose)
    compileOnly(libs.glide.compose)

    implementation(project(":lib_util"))
}