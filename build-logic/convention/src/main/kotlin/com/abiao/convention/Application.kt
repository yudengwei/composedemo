package com.abiao.convention

import com.abiao.Constant
import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

internal fun Project.configureAndroidApplication(commonExtension: BaseAppModuleExtension) {
    commonExtension.apply {
        compileSdk = Constant.COMPILER_VERSION
        defaultConfig {
            applicationId = "com.abiao.composedemo"
            minSdk = Constant.MIN_SDK
            targetSdk = Constant.TARGET_VERSION
            versionCode = Constant.VERSION_CODE
            versionName = Constant.VERSION_NAME
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            applicationVariants.all {
                val variant = this
                outputs.all {
                    if (this is ApkVariantOutputImpl) {
                        this.outputFileName =
                            //"compose_${variant.name}_versionCode_${variant.versionCode}_versionName_${variant.versionName}_${getApkBuildTime()}.apk"
                            "compose_${variant.name}_versionCode_${variant.versionCode}_versionName_${variant.versionName}.apk"
                    }
                }
            }
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

fun getApkBuildTime(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US)
    simpleDateFormat.timeZone = TimeZone.getTimeZone("Asia/Shanghai")
    val time = Calendar.getInstance().time
    return simpleDateFormat.format(time)
}