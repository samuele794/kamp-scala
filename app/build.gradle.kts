import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.firebase.appdistribution")
    id(Dependencies.Plugins.ksp) version Version.kspVersion
}

apply(plugin = "com.google.gms.google-services")
apply(plugin = "com.google.firebase.crashlytics")

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

android {

    lint {
        isAbortOnError = false
    }

    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "it.samuele794.scala.android"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String", "GOOGLE_AUTH_APIKEY", "\"${localProperties["auth.google.apikey"]}\""
        )

        buildConfigField(
            "String", "EMAIL_TEST_AN", "\"${localProperties["auth.email"]}\""
        )

        buildConfigField(
            "String", "PASSWORD_TEST_AN", "\"${localProperties["auth.password"]}\""
        )

        resValue("string", "maps_api_key", localProperties["maps.map.apikey"].toString())
    }
    packagingOptions {
        resources.excludes.add("META-INF/*.kotlin_module")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            firebaseAppDistribution {
                appId = localProperties.getProperty("firebase.projectID")
                artifactType = "APK"
                testers = localProperties.getProperty("firebase.testers")
            }

        }
    }

    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lint {
        isWarningsAsErrors = false
        isAbortOnError = false
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

buildscript {
    repositories {
        google()
    }

    dependencies {
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.0")
    }
}

dependencies {
    implementation(project(":shared"))
//    implementation(libs.bundles.app.ui)
    coreLibraryDesugaring(Dependencies.Jetpack.desugaring)

    with(Dependencies.Koin) {
        implementation(android)
        implementation(compose)
    }

    with(Dependencies.Jetpack) {
        implementation(core)
        with(Dependencies.Jetpack.Compose) {
            implementation(ui)
            debugImplementation(uiTool)
            implementation(toolPreview)

            implementation(material)
            implementation(materialIconExtended)
            implementation(activity)
            implementation(constraint)
            implementation(maps)
            implementation(lottie)
        }
    }

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.1")


    implementation(platform("com.google.firebase:firebase-bom:29.3.1"))

    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    // Also declare the dependency for the Google Play services library and specify its version
    implementation("com.google.android.gms:play-services-auth:20.2.0")

    // https://composedestinations.rafaelcosta.xyz/
    implementation("io.github.raamcosta.compose-destinations:core:1.5.8-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.5.8-beta")

}
