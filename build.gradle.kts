// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        // maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(Dependencies.GradlePlugin.kotlinGradlePluginVersion)
        classpath(Dependencies.GradlePlugin.androidToolsBuild)
        classpath(Dependencies.GradlePlugin.mokoResourceGenerator)
        classpath(Dependencies.GradlePlugin.googleAppDistribution)
        classpath(Dependencies.GradlePlugin.buildKonfig)
//        classpath(Dependencies.GradlePlugin.ktLint)

        // val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")
        //     as org.gradle.accessors.dm.LibrariesForLibs
        // classpath(libs.bundles.gradlePlugins)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build gradle files
    }
}

// https://youtrack.jetbrains.com/issue/KTIJ-19369
//@Suppress("DSL_SCOPE_VIOLATION")
//plugins {
//    alias(libs.plugins.gradleDependencyUpdate)
//}

allprojects {
    repositories {
        google()
        mavenCentral()
//        maven("https://repo1.maven.org/maven2/dev/gitlive/")
    }
}

//subprojects {
//    apply(plugin = "org.jlleitschuh.gradle.ktlint")
//
//    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
//        enableExperimentalRules.set(true)
//        verbose.set(true)
//        filter {
//            exclude { it.file.path.contains("build/") }
//        }
//    }
//
//    afterEvaluate {
//        tasks.named("check").configure {
//            dependsOn(tasks.getByName("ktlintCheck"))
//        }
//    }
//}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
