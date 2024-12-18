plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "Nexus"

include("nexus-api")
include("nexus-core")
include("nexus-docs")
include("nexus-docs-generator")
include("nexus-plugin")
