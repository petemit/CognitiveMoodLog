// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val compose_version by extra("1.3.0")
    val navigation_version by extra("2.5.0")
    repositories {
        google()
        mavenCentral()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath(kotlin("gradle-plugin", version = "1.7.10"))
        classpath("io.realm:realm-gradle-plugin:10.7.0")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = java.net.URI("https://kotlin.bintray.com/kotlinx")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}//}