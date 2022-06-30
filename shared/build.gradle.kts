import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.util.Properties

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin(Dependencies.Plugins.kotlinSerialization) version Version.kotlinVersion
    id(Dependencies.Plugins.androidLibrary)
    id(Dependencies.Plugins.completeKotlin) version Version.completeKotlinVersion
    id(Dependencies.Plugins.multiplatformResources)
    id(Dependencies.Plugins.buildKonfig)
    id(Dependencies.Plugins.ksp) version Version.kspVersion
    id("kotlin-parcelize")
}

val gradleProperties = Properties()
gradleProperties.load(project.rootProject.file("gradle.properties").inputStream())


android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    lint {
        isWarningsAsErrors = false
        isAbortOnError = false
    }
}

version = "1.2"

android {
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}

kotlin {
    android()

    if (gradleProperties["kotlin.mpp.iosBuild"].toString().toBoolean()) {
        ios()
        // Note: iosSimulatorArm64 target requires that all dependencies have M1 support
        iosSimulatorArm64()
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }

    // Enable concurrent sweep phase in new native memory manager. (This will be enabled by default in 1.7.0)
    // https://kotlinlang.org/docs/whatsnew1620.html#concurrent-implementation-for-the-sweep-phase-in-new-memory-manager
    targets.withType<KotlinNativeTarget> {
        binaries.all {
            freeCompilerArgs += "-Xgc=cms"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Koin.core)
                implementation(Dependencies.Coroutines.core)

                api(Dependencies.kermit)
                api(Dependencies.kermitCrashlytics)

                implementation(Dependencies.serialization)

                with(Dependencies.Ktor) {
                    implementation(core)
                    implementation(cio)
                    implementation(logging)
                    implementation(contentNegotiation)
                    implementation(json)
                }

                with(Dependencies.Moko) {
                    api(resources)
                    api(parcelize)
                }

                api(Dependencies.dateTime)
                implementation(Dependencies.ktorFit)

                // https://github.com/GitLiveApp/firebase-kotlin-sdk
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.shared.commonTest)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.ktor.client.okHttp)

                with(Dependencies.Jetpack.Lifecycle) {
                    implementation(runtime)
                    implementation(viewmodel)
                }

                api(Dependencies.Moko.compose)

                //TODO REMOVE WHEN MIGRATION FINISH
                implementation("com.google.android.gms:play-services-auth:20.1.0")
                // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-play-services
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.1")

                api("com.google.android.gms:play-services-maps:18.0.2")

                implementation("com.firebase:geofire-android-common:3.2.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(libs.bundles.shared.androidTest)
            }
        }

        if (gradleProperties["kotlin.mpp.iosBuild"].toString().toBoolean()) {
            val iosMain by getting {
                dependencies {
                    implementation(libs.ktor.client.ios)
                }
            }
            val iosTest by getting
            val iosSimulatorArm64Main by getting {
                dependsOn(iosMain)
            }
            val iosSimulatorArm64Test by getting {
                dependsOn(iosTest)
            }
        }
    }

    sourceSets.matching { it.name.endsWith("Test") }
        .configureEach {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
        }

    cocoapods {
        summary = "Common library for the KaMP starter kit"
        homepage = "https://github.com/touchlab/KaMPKit"
        framework {
            isStatic = true // SwiftUI preview requires dynamic framework
        }
        ios.deploymentTarget = "14.1"
        podfile = project.file("../ios/Podfile")

        pod("Parse")
        pod("ParseSwift")
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "it.samuele794.scala.resources"
    multiplatformResourcesClassName = "SharedRes"
}

buildkonfig {
    packageName = "it.samuele794.scala"

    defaultConfigs {
        buildConfigField(STRING, "GOOGLE_API_BASE_URL", "https://maps.googleapis.com/")
    }
}

dependencies {
    add("kspCommonMainMetadata", "de.jensklingenberg.ktorfit:ktorfit-ksp:${Version.ktorfitVersion}")
    add("kspAndroid", "de.jensklingenberg.ktorfit:ktorfit-ksp:${Version.ktorfitVersion}")
    if (gradleProperties["kotlin.mpp.iosBuild"].toString().toBoolean()) {
        add("kspIosX64", "de.jensklingenberg.ktorfit:ktorfit-ksp:${Version.ktorfitVersion}")
        add("kspIosArm64", "de.jensklingenberg.ktorfit:ktorfit-ksp:${Version.ktorfitVersion}")
        add("kspIosSimulatorArm64", "de.jensklingenberg.ktorfit:ktorfit-ksp:${Version.ktorfitVersion}")
    }
}