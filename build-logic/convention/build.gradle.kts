import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}


dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
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
        register("Niahilt") {
            id = "abiao.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("jvmLibrary") {
            id = "abiao.jvm"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("room") {
            id = "abiao.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}

