// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.google.com")
        maven("https://jitpack.io")
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.google.services)
        classpath(libs.firebase.crashlytics.gradle)
    }
}
plugins {
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}
allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.google.com")
        maven("https://jitpack.io")
    }
}
tasks.register("clean",Delete::class){
    delete(rootProject.layout.buildDirectory)
}
