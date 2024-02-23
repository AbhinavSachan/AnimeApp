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
        classpath("com.android.tools.build:gradle:8.2.2") // Replace with the version you want
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22") // Replace with the version you want
    }
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
    delete(rootProject.buildDir)
}
