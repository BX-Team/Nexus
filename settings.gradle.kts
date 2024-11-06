plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "Nexus"

include("nexus-api")
include("nexus-plugin")
include("nexus-core")
