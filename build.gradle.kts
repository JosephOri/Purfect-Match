// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
repositories {
        google()
        mavenCentral()
    }
    dependencies {

        classpath(libs.google.services)
        classpath(libs.gradle)
        val nav_version = "2.7.7"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.android.tools.build:gradle:8.5.0")
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin) apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false

}