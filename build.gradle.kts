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
    version = "IC-2018.2" //Ifor a full list of IntelliJ IDEA releases please see https://www.jetbrains.com/intellij-repository/releases
    pluginName = "event-sounds"
}

group = "com.github.essquilo"
version = "0.1"


dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.googlecode.soundlibs", "jlayer", "1.0.1-1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}