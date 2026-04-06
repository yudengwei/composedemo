plugins {
    alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.abiao.room)
    alias(libs.plugins.abiao.hilt)
}

android {
    namespace = "com.abiao.database"
}

dependencies {
    implementation(projects.libNowinandroid.model)

    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}