import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


buildscript {
    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:1.4.3")
    }
}

plugins {
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.compose") version "1.0.0-alpha2"
    id("com.squareup.sqldelight") version "1.4.3"
}

group = "me.xing"
version = "1.0"

kotlin {
    sourceSets {
        main {
            dependencies {
                implementation("com.squareup.sqldelight:sqlite-driver:1.4.3")
            }
        }
    }
}

sqldelight {
    database("CommonDatabase") {
        packageName = "xing2387.repos.db"
    }
}

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
    implementation("org.bouncycastle:bcpkix-jdk14:1.69")
    implementation("com.google.code.gson:gson:2.8.8")

    implementation(kotlin("reflect"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "14"
//        freeCompilerArgs = ArrayList<String>().also {
//            it.addAll(freeCompilerArgs)
//            it.add("-Xallow-jvm-ir-dependencies")
//            it.add("-P")
//            it.add("plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true")
//        }
    }
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