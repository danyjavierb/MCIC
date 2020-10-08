import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
object Version {
    const val arrow = "0.10.5"
    const val jacksonKotlin =  "2.11.2"
}

object Libs {
    const val arrowFx = "io.arrow-kt:arrow-fx:${Version.arrow}"
    const val arrowMtl = "io.arrow-kt:arrow-mtl:${Version.arrow}"
    const val arrowSyntax = "io.arrow-kt:arrow-syntax:${Version.arrow}"
    const val jacksonKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:${Version.jacksonKotlin}"
}

 val implementation = "implementation"

plugins {
    id("org.springframework.boot") version "2.3.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
}

group = "com.danyjavierb"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.drools:drools-core:7.18.0.Final")
    implementation("org.kie:kie-spring:7.18.0.Final")
    implementation.let {
        it(Libs.arrowFx)
        it(Libs.arrowMtl)
        it(Libs.arrowSyntax)
    }
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
