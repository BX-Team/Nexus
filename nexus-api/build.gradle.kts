plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.lombok)
}

group = project.group
version = project.version

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.paper)
}

tasks {
    java {
        withSourcesJar()
        withJavadocJar()
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        val options = options as StandardJavadocDocletOptions
        options.encoding = Charsets.UTF_8.name()
        options.overview = "src/main/javadoc/overview.html"
        options.use()
        options.tags("apiNote:a:API Note:")
    }
}

publishing {
    repositories {
        maven {
            name = "nexus"
            url = uri("https://repo.bx-team.space/releases/")

            if (version.toString().endsWith("-SNAPSHOT")) {
                url = uri("https://repo.bx-team.space/snapshots/")
            }

            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = group.toString()
            artifactId = "nexus"
            version = version.toString()
            from(components["java"])
        }
    }
}
