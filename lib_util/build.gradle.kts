plugins {
    alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.androidx.hilt)
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "com.abiao.util"

    resourcePrefix("util")
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.hilt)
    ksp(libs.androidx.hilt.compiler)
}