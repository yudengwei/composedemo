plugins {
    alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.abiao.hilt)
}

android {
    namespace = "com.abiao.sync.work"
}

dependencies {
    ksp(libs.hilt.ext.compiler)

    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)

    implementation(projects.libNowinandroid.data)
    implementation(projects.libNowinandroid.model)
    implementation(projects.libCommon)
    implementation(projects.libUtil)
}