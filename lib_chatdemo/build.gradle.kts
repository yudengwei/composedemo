plugins {
    alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.abiao.hilt)
    alias(libs.plugins.abiao.android.compose)
    id("kotlinx-serialization")
}

android {
    namespace = "com.abiao.lib_chatdemo"
}

dependencies {
    implementation(projects.libUtil)
    implementation(projects.libCommon)

    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
}