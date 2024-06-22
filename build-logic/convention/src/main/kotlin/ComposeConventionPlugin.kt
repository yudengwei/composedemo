import com.abiao.convention.configureCompose
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

class ComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val applicationExtension = extensions.findByType<ApplicationExtension>()
            applicationExtension?.let {
                configureCompose(commonExtension = it)
            }
            val libraryExtension = extensions.findByType<LibraryExtension>()
            libraryExtension?.let {
                configureCompose(commonExtension = it)
            }
        }
    }
}