
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}
android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId("com.mindbuilders.cognitivemoodlog")
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode(20)
        versionName("2.0.0")

        testInstrumentationRunner("android.support.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        named("release") {
            minifyEnabled(true)
            proguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility =JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    // compose
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.0-beta01"
    }
}

dependencies {

    //dagger
    implementation("com.google.dagger:dagger:2.28.3")
    kapt("com.google.dagger:dagger-compiler:2.28.3")

    //architecture components
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.4")
    implementation("androidx.navigation:navigation-compose:1.0.0-alpha09")
    implementation("androidx.room:room-ktx:2.2.6")
    implementation("androidx.room:room-runtime:2.2.6")
    kapt("androidx.room:room-compiler:2.2.6")
}
//
//ext.supportLibraryVersion = '26.1.0'
//dependencies {
//    compile fileTree(include: ['*.jar'], dir: 'libs')
//    compile 'com.android.support.constraint:constraint-layout:1.0.2'
//    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
//        exclude group: 'com.android.support', module: 'support-annotations'
//    })
//    compile "com.android.support:appcompat-v7:$supportLibraryVersion"
//    compile "com.android.support:design:$supportLibraryVersion"
//    compile "com.android.support:preference-v7:$supportLibraryVersion"
//    compile "com.android.support:support-v4:$supportLibraryVersion"
//    compile 'pub.devrel:easypermissions:0.3.0'
//    compile('com.google.api-client:google-api-client-android:1.23.0') {
//        exclude group: 'org.apache.httpcomponents'
//    }
//    compile('com.google.apis:google-api-services-drive:v3-rev103-1.23.0') {
//        exclude group: 'org.apache.httpcomponents'
//    }
//    compile 'com.google.android.gms:play-services-auth:11.6.0'
//    compile 'net.zetetic:android-database-sqlcipher:3.5.7@aar'
//    debugCompile 'com.facebook.stetho:stetho:1.5.0'
//    compile 'com.github.JakeWharton:ViewPagerIndicator:2.4.1@aar'
//    testCompile 'junit:junit:4.12'
//
//    compile group: 'com.opencsv', name: 'opencsv', version: '4.1'
//}
apply(from = "signingconfig.gradle")
