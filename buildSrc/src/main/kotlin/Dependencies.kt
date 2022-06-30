object Version {
    const val kotlinVersion = "1.6.20"
    const val kotlinXSerializationVersion = "1.3.3"
    const val coroutineVersion = "1.6.1"

    const val androidToolsBuildVersion = "7.0.4"

    const val mokoResources = "0.20.1"
    const val mokoParcelize = "0.8.0"

    const val googleAppDistribution = "3.0.1"
    const val buildKonfigVersion = "0.11.0"

    const val completeKotlinVersion = "1.1.0"

    const val kspVersion = "${kotlinVersion}-1.0.4"

    const val koinVersion = "3.1.6"

    const val kermitVersion = "1.1.1"

    const val ktorVersion = "2.0.1"

    const val dateTimeVersion = "0.3.2"

    const val ktorfitVersion = "1.0.0-beta06"

    const val ktLintVersion = "10.2.1"

    const val jetpackLifecycleVersion = "2.4.1"

    const val desugaringVersion = "1.1.5"

    const val androidXCore = "1.8.0"

    const val composeUiVersion = "1.1.1"
    const val composeActivityVersion = "1.4.0"
    const val composeContraint = "1.0.1"

    const val composeMaps = "2.1.1"
    const val composeLottie = "5.1.1"

}

object Dependencies {

    object GradlePlugin {
        const val kotlinGradlePluginVersion = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinVersion}"
        const val androidToolsBuild = "com.android.tools.build:gradle:${Version.androidToolsBuildVersion}"

        const val mokoResourceGenerator = "dev.icerock.moko:resources-generator:${Version.mokoResources}"

        const val googleAppDistribution = "com.google.firebase:firebase-appdistribution-gradle:${Version.googleAppDistribution}"

        const val buildKonfig = "com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:${Version.buildKonfigVersion}"

        const val ktLint = "org.jlleitschuh.gradle:ktlint-gradle:${Version.ktLintVersion}"
    }

    object Plugins {
        const val kotlinSerialization = "plugin.serialization"
        const val androidLibrary = "com.android.library"

        // https://github.com/LouisCAD/CompleteKotlin
        const val completeKotlin ="com.louiscad.complete-kotlin"

        const val multiplatformResources = "dev.icerock.mobile.multiplatform-resources"

        const val buildKonfig = "com.codingfeline.buildkonfig"

        const val ksp = "com.google.devtools.ksp"
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${Version.koinVersion}"
        const val android = "io.insert-koin:koin-android:${Version.koinVersion}"
        const val compose = "io.insert-koin:koin-androidx-compose:${Version.koinVersion}"
    }

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutineVersion}"
    }

    object Ktor {
        const val core = "io.ktor:ktor-client-core:${Version.ktorVersion}"
        const val cio = "io.ktor:ktor-client-cio:${Version.ktorVersion}"
        const val logging = "io.ktor:ktor-client-logging:${Version.ktorVersion}"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${Version.ktorVersion}"
        const val json = "io.ktor:ktor-serialization-kotlinx-json:${Version.ktorVersion}"
    }

    object Moko {
        const val resources = "dev.icerock.moko:resources:${Version.mokoResources}"
        const val parcelize = "dev.icerock.moko:parcelize:${Version.mokoParcelize}"
        const val compose = "dev.icerock.moko:resources-compose:${Version.mokoResources}"
    }

    object Jetpack {
        object Lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.jetpackLifecycleVersion}"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.jetpackLifecycleVersion}"
        }

        object Compose {
            const val ui = "androidx.compose.ui:ui:${Version.composeUiVersion}"
            const val material = "androidx.compose.material:material:${Version.composeUiVersion}"
            const val materialIconExtended = "androidx.compose.material:material-icons-extended:${Version.composeUiVersion}"
            const val constraint = "androidx.constraintlayout:constraintlayout-compose:${Version.composeContraint}"

            const val activity = "androidx.activity:activity-compose:${Version.composeActivityVersion}"

            const val uiTool = "androidx.compose.ui:ui-tooling:${Version.composeUiVersion}"
            const val toolPreview = "androidx.compose.ui:ui-tooling-preview:${Version.composeUiVersion}"

            const val maps = "com.google.maps.android:maps-compose:${Version.composeMaps}"
            const val lottie = "com.airbnb.android:lottie-compose:${Version.composeLottie}"
        }

        const val desugaring = "com.android.tools:desugar_jdk_libs:${Version.desugaringVersion}"

        const val core = "androidx.core:core-ktx:${Version.androidXCore}"
    }



    const val kermit = "co.touchlab:kermit:${Version.kermitVersion}"
    const val kermitCrashlytics = "co.touchlab:kermit-crashlytics:${Version.kermitVersion}"

    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.kotlinXSerializationVersion}"

    const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Version.dateTimeVersion}"

    const val ktorFit = "de.jensklingenberg.ktorfit:ktorfit-lib:${Version.ktorfitVersion}"
}