import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

plugins {
    java
    id("io.quarkus")
    id("org.jreleaser") version "1.13.1"
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-picocli")
    implementation("io.quarkus:quarkus-smallrye-graphql-client")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-rest-client-jackson")
    testImplementation("io.quarkus:quarkus-junit5")
}

group = "com.linux"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.register("version") {
    doLast {
        println(project.version)
    }
}

tasks.register("setVersion") {
    val gradlePropertiesPath = Paths.get("gradle.properties")
    doLast {
        if (project.hasProperty("newVersion")) {
            val properties = Properties().also { p ->
                Files.newBufferedReader(gradlePropertiesPath, StandardCharsets.UTF_8).use {
                    p.load(it)
                }
            }

            val newVersion = project.properties["newVersion"]?.toString() ?: project.version.toString()
            logger.lifecycle("Setting version to $newVersion")
            properties.setProperty("version", newVersion)

            Files.newBufferedWriter(gradlePropertiesPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE).use {
                properties.store(it, "Version Updated By Gradle Task setVersion")
            }
        } else {
            logger.warn("Please provide newVersion property like -P newVersion=x.x.x to setVersion task to update the version")
        }
    }
}