plugins {
    alias(libs.plugins.abiao.hilt)
    alias(libs.plugins.abiao.android.library)
}

android {
    namespace = "com.abiao.datastore.test"
}

dependencies {
    implementation(libs.hilt.android.testing)
    implementation(projects.libCommon)
    implementation(projects.libNowinandroid)
    implementation(projects.libNowinandroid.data)
    implementation(libs.androidx.datastore)
    implementation(projects.libNowinandroid.datastoreProto)
}

