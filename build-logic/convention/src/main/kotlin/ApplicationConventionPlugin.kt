import com.abiao.convention.configureAndroidApplication
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.plugin.parcelize")
            }
            extensions.configure<ApplicationExtension> {
                configureAndroidApplication(commonExtension = this)
            }
        }
    }
}
