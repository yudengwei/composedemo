import com.abiao.convention.configureAndroidLibrary
import com.abiao.libs
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class LibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.plugin.parcelize")
            }
            extensions.configure<LibraryExtension> {
                configureAndroidLibrary(commExtension = this)
                testOptions.targetSdk = 36
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testOptions.animationsDisabled = true
            }

            dependencies {
                "androidTestImplementation"(libs.findLibrary("kotlin.test").get())
                "testImplementation"(libs.findLibrary("kotlin.test").get())
                "testImplementation"(libs.findLibrary("junit.junit").get())

                "implementation"(libs.findLibrary("androidx.tracing.ktx").get())
            }
        }
    }
}
