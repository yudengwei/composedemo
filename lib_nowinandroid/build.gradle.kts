plugins {
    alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.abiao.android.compose)
    alias(libs.plugins.abiao.hilt)
}

android {
    namespace = "com.abiao.lib_nowinandroid"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(projects.libUtil)
    implementation(projects.libCommon)
    implementation(projects.libNowinandroid.data)

    implementation(libs.androidx.compose.adaptive.core)
    implementation(libs.androidx.compose.adaptive.layout)
    implementation(libs.androidx.compose.adaptive.navigation)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(projects.libNowinandroid.datastoreTest)
}