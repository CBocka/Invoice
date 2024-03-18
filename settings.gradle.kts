pluginManagement {
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

rootProject.name = "Invoice"
include(":app")
include(":features:account")
include(":infrastructure:firebase")
include(":infrastructure:printer")
include(":features:customer")
include(":domain:invoice")
include(":features:featureinvoice")
include(":features:item")
include(":features:task")
