plugins {
    alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.abiao.android.compose)
    alias(libs.plugins.abiao.hilt)
    id("kotlinx-serialization")
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
    implementation(projects.libNowinandroid.sync.work)
    implementation(projects.libNowinandroid.model)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.compose.adaptive.core)
    implementation(libs.androidx.compose.adaptive.layout)
    implementation(libs.androidx.compose.adaptive.navigation)
    implementation(libs.androidx.compose.adaptive.navigation3)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(projects.libNowinandroid.datastoreTest)

    api(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewModel.navigation3)
    implementation(libs.androidx.hilt.lifecycle.viewModelCompose)

    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3.adapter.suite)
}