// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val compose_version by extra("1.0.0-beta03")
    repositories {
        google()
        mavenCentral()
        jcenter()  //todo bummer... realm needs this
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha12")
        classpath(kotlin("gradle-plugin", version = "1.4.31"))
        classpath("io.realm:realm-gradle-plugin:10.2.0")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()  //todo bummer... realm needs this
        maven {
            url = java.net.URI("https://kotlin.bintray.com/kotlinx")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}//}