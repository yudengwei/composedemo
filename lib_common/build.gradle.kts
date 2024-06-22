@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.abiao.android.compose)
}

android {
    namespace = "com.abiao.common"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)

    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

    implementation(libs.androidx.startup.runtime)

    implementation(project(":lib_util"))
}