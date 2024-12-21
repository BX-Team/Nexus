plugins {
    `nexus-java`
    `nexus-repositories`
    `nexus-publish`
}

dependencies {
    compileOnly(libs.paper)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks {
    javadoc {
        val options = options as StandardJavadocDocletOptions
        options.encoding = Charsets.UTF_8.name()
        options.overview = "src/main/javadoc/overview.html"
        options.use()
        options.tags("apiNote:a:API Note:")
    }
}
