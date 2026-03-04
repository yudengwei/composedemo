package com.abiao.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureCompose(commonExtension: CommonExtension) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    commonExtension.apply {
        buildFeatures.compose = true

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            "implementation"(platform(bom))
            "implementation"(libs.findLibrary("androidx-compose-preview").get())
            "implementation"(libs.findLibrary("androidx-compose-ui").get())
            "implementation"(libs.findLibrary("androidx-compose-foundation").get())
            "implementation"(libs.findLibrary("androidx-compose-material3").get())
            "implementation"(libs.findLibrary("androidx-compose-materialWindow").get())
            "implementation"(libs.findLibrary("androidx-activity-compose").get())
            "debugImplementation"(libs.findLibrary("androidx-compose-tooling").get())
        }
    }
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            optIn.addAll(
                listOf(
                    "androidx.compose.foundation.ExperimentalFoundationApi",
                    "androidx.compose.foundation.layout.ExperimentalLayoutApi",
                    "com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi",
                    "androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi",
                    "androidx.compose.animation.ExperimentalAnimationApi",
                    "androidx.compose.material.ExperimentalMaterialApi",
                    "androidx.compose.material3.ExperimentalMaterial3Api",
                )
            )
        }
    }
}
