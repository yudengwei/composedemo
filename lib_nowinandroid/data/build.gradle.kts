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
    implementation(projects.libNowinandroid.database)
    implementation(projects.libNowinandroid.network)

    implementation(libs.androidx.datastore)

    implementation(libs.kotlinx.datetime)
}