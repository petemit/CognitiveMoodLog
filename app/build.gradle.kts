
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

//apply plugin: 'com.android.application'
//
//android {
//    compileSdkVersion 26
//    buildToolsVersion "26.0.2"
//    defaultConfig {
//        applicationId "com.mindbuilders.cognitivemoodlog"
//        minSdkVersion 19
//        targetSdkVersion 26
//        versionCode 13
//        versionName "1.30"
//
//        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
//    }
//    buildTypes {
//        release {
//            minifyEnabled false
//            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
//        }
//    }
//}
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
//apply from: 'signingconfig.gradle'
