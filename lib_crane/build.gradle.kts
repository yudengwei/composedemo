@file:Suppress("UnstableApiUsage")

plugins {
    //alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.abiao.android.application)
    alias(libs.plugins.abiao.android.compose)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.androidx.hilt)
}

android {
    namespace = "com.abiao.crane"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.lifecycle.viewModelCompose)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.hilt)
    ksp(libs.androidx.hilt.compiler)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.materialWindow)
    implementation(libs.androidx.compose.livedata)

    implementation(libs.coil.compose)

    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

    implementation(project(":lib_util"))
    implementation(project(":lib_common"))
}