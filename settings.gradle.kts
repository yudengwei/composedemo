pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "composedemo"
include(":app")
include(":lib_imagepick")
include(":lib_util")
include(":lib_crane")
include(":lib_animator")
include(":lib_common")
include(":lib_demo")
include(":lib_canvas")
include(":lib_touch")
include(":lib_draw")
include(":lib_nowinandroid")
