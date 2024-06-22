plugins {
    alias(libs.plugins.abiao.android.library)
}

android {
    namespace = "com.abiao.util"

    resourcePrefix("util")
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)
}