package com.abiao.convention

import com.abiao.Constant
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureAndroidLibrary(commExtension : LibraryExtension) {
    commExtension.apply {
        compileSdk = Constant.COMPILER_VERSION
        defaultConfig {
            minSdk = Constant.MIN_SDK
        }
        buildFeatures {
            buildConfig = false
        }
        lint {
            checkDependencies = true
        }
        compileOptions {
            sourceCompatibility = Constant.JAVA_VERSION
            targetCompatibility = Constant.JAVA_VERSION
        }
        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = Constant.JAVA_VERSION.toString()
            }
        }
    }
}