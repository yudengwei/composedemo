package com.abiao.convention

import com.abiao.Constant
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import java.io.File

internal fun Project.configureAndroidApplication(commonExtension: ApplicationExtension) {
    commonExtension.apply {
        compileSdk = Constant.COMPILER_VERSION
        defaultConfig {
            applicationId = "com.abiao.composedemo"
            minSdk = Constant.MIN_SDK
            targetSdk = Constant.TARGET_VERSION
            versionCode = Constant.VERSION_CODE
            versionName = Constant.VERSION_NAME
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

        signingConfigs {
            create("release") {
                storeFile =
                    File(rootDir.absolutePath + File.separator + "doc" + File.separator + "abiao.jks")
                keyAlias = "key0"
                keyPassword = "123456"
                storePassword = "123456"
                enableV1Signing = true
                enableV2Signing = true
            }
        }
        buildTypes {
            debug {
                signingConfig = signingConfigs.getByName("release")
                isMinifyEnabled = false
                isShrinkResources = false
                isDebuggable = true
            }
            release {
                signingConfig = signingConfigs.getByName("release")
                isMinifyEnabled = true
                isShrinkResources = true
                isDebuggable = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        packaging {
            jniLibs {
                excludes += setOf("META-INF/{AL2.0,LGPL2.1}")
            }
            resources {
                excludes += setOf(
                    "**/*.md",
                    "**/*.properties",
                    "**/**/*.properties",
                    "META-INF/{AL2.0,LGPL2.1}",
                    "META-INF/CHANGES",
                    "DebugProbesKt.bin",
                    "kotlin-tooling-metadata.json"
                )
            }
        }
    }
}
