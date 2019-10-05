// Kotlin Gradle DSL
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion by System.getProperties()
    id("org.jetbrains.kotlin.jvm") version "$kotlinVersion"
    id("org.jetbrains.kotlin.plugin.spring") version "$kotlinVersion"
    id("org.jetbrains.kotlin.plugin.jpa") version "$kotlinVersion"
    id("org.jetbrains.kotlin.plugin.allopen") version "$kotlinVersion" apply true
    id("org.jetbrains.kotlin.plugin.noarg") version "$kotlinVersion" apply true
    id("org.springframework.boot") version "2.1.8.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("org.liquibase.gradle") version "2.0.1"
    kotlin("kapt") version "$kotlinVersion"
}

group = "spring.boot.kotlin.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "Greenwich.SR3"

// https://stackoverflow.com/questions/45753733/ext-in-buildscript-can-not-be-recognised-by-gradle-kotlin-dsl
object DependencyVersions {
    const val mapStructVersion = "1.3.1.Final"
    const val liquibaseHibernate5Version = "3.8"
    const val junit5Version = "5.2.0"
    const val mockkVersion = "1.9.3"
    const val jjwtVersion = "0.9.1"
    const val javaFakerVersion = "1.0.1"
    const val assertjVersion = "3.11.1"
    const val liquibaseCoreVersion = "3.8.0"
    const val kotlinLoggingVersion = "1.7.6"
    const val springMockkVersion = "1.1.3"
}

dependencies {
    val kotlinVersion by System.getProperties()
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    runtimeOnly("mysql:mysql-connector-java")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:${DependencyVersions.junit5Version}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${DependencyVersions.junit5Version}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${DependencyVersions.junit5Version}")

    testImplementation("org.mockito:mockito-core:3.1.0")
    testImplementation("org.mockito:mockito-junit-jupiter:3.1.0")

    testImplementation("org.assertj:assertj-core:${DependencyVersions.assertjVersion}")

    testImplementation("io.mockk:mockk:${DependencyVersions.mockkVersion}")
    testImplementation("com.ninja-squad:springmockk:1.1.3")

    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test"){
        exclude(group="junit", module="junit")
    }
    testImplementation("io.projectreactor:reactor-test")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

/*
The generated constructor is synthetic so it can’t be directly called from Java or Kotlin,
 but it can be called using reflection. See
 https://kotlinlang.org/docs/reference/compiler-plugins.html#no-arg-compiler-plugin
 */
noArg {
    annotation("javax.persistence.Entity")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xallow-result-return-type")
        jvmTarget = "1.8"
    }
}