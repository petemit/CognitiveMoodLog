plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("realm-android")
    id("kotlin-parcelize")
}
android {
    namespace = "com.mindbuilders.cognitivemoodlog"
    compileSdkVersion = "android-33"
    defaultConfig {
        applicationId = "com.mindbuilders.cognitivemoodlog"
        minSdk = 21
        targetSdk = 33
        versionCode = 22
        versionName = "2.2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        //useIR = true
    }

    packagingOptions {
        resources {
            excludes += "META-INF/"
            excludes += "okhttp3/"
            excludes += "kotlin/"
            excludes += "org/"
            excludes += ".properties"
            excludes += ".bin"
        }
    }

    // compose
    buildFeatures {
        viewBinding = true
        compose = true
        dataBinding = true

    }


    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
        kotlinCompilerVersion = "1.7.1"
    }
}

dependencies {

    //dagger
    implementation("com.google.dagger:dagger:2.46.1")
    implementation("com.google.dagger:dagger-android:2.46.1")

    kapt("com.google.dagger:dagger-compiler:2.46.1")
    kapt("com.google.dagger:dagger-android-processor:2.46.1")

    //core
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("com.google.code.gson:gson:2.8.6")

    //compose
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.runtime:runtime-livedata:${rootProject.extra["compose_version"]}")

    //architectural components
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha01")
    implementation("androidx.activity:activity-compose:1.3.0")

    //navigation
    implementation("androidx.navigation:navigation-runtime:${rootProject.extra["navigation_version"]}")
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["navigation_version"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["navigation_version"]}")
    implementation("androidx.navigation:navigation-compose:2.4.0")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
apply(from = "signingconfig.gradle")
