-keepclassmembers class net.sqlcipher.** {
    *;
}

-keep class net.sqlcipher.database.SQLiteException {
    *;
}

-keepnames class * implements android.os.Parcelable {
   *;
}