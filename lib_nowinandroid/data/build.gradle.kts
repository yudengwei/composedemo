plugins {
    alias(libs.plugins.abiao.android.library)
    alias(libs.plugins.abiao.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.abiao.data"
}

dependencies {
    implementation(projects.libUtil)
    implementation(projects.libCommon)
    implementation(projects.libNowinandroid.datastoreProto)
    implementation(projects.libNowinandroid.model)

    implementation(libs.androidx.datastore)
}