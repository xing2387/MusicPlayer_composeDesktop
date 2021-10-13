import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    //This version (1.1.0-alpha01) of the Compose Compiler requires Kotlin version 1.5.21 but you appear to be using Kotlin version 1.5.31 which is not known to be compatible.
    //or suppressKotlinVersionCompatibilityCheck
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.compose") version "1.0.0-alpha3"
}

group = "me.xing"
version = "1.0"

repositories {
    mavenCentral()
    google()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("androidx.annotation:annotation:1.2.0")
    implementation("com.github.skydoves:landscapist-glide:1.0.7")
    implementation(kotlin("reflect"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "14"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MusicPlayer"
            packageVersion = "1.0.0"
        }
    }
}