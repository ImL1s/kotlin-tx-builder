plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    `maven-publish`
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
        publishLibraryVariants("release")
    }

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "txbuilder"
        }
    }

    listOf(
        watchosArm64(),
        watchosX64(),
        watchosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "txbuilder"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation("io.github.iml1s:crypto-core:1.3.0")
            implementation("io.github.iml1s:kotlin-address:1.3.0")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "io.github.iml1s.tx"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }
}

tasks.configureEach {
    if (name.contains("lintVitalAnalyzeRelease")) {
        enabled = false
    }
}

