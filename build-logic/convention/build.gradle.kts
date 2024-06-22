import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradle)
    compileOnly(libs.kotlin.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "abiao.android.application"
            implementationClass = "ApplicationConventionPlugin"
        }
        register("androidCompose") {
            id = "abiao.android.compose"
            implementationClass = "ComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "abiao.android.library"
            implementationClass = "LibraryConventionPlugin"
        }
    }
}

