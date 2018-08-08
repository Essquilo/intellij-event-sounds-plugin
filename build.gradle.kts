import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("idea")
    id("org.jetbrains.intellij") version "0.3.5"
    java
    kotlin("jvm") version "1.2.51"
}

repositories {
    mavenCentral()
}

intellij {
    version = "2018.2" //for a full list of IntelliJ IDEA releases please see https://www.jetbrains.com/intellij-repository/releases
    updateSinceUntilBuild = false
    pluginName = "event-sounds"
}

group = "com.github.essquilo"
version = "0.1.1"

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.googlecode.soundlibs", "jlayer", "1.0.1-1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
