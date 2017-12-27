package com.mindbuilders.cognitivemoodlog;

import android.app.Application;

import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;

import static net.sqlcipher.database.SQLiteDatabase.loadLibs;

/**
 * Created by Peter on 12/4/2017.
 */

public class BaseApplication extends Application {
    private static CogMoodLogDatabaseHelper dbHelper;
    public static String passwordHash="";
    @Override
    public void onCreate() {
        loadLibs(getApplicationContext());
        BaseApplication.setDbHelper(new CogMoodLogDatabaseHelper(getBaseContext()));
        super.onCreate();

        if (BuildConfig.DEBUG) {
            StethoEnabler.enable(this);
        }

    }

    public static CogMoodLogDatabaseHelper getDbHelper() {
        return dbHelper;
    }

    public static void setDbHelper(CogMoodLogDatabaseHelper dbHelper) {
        BaseApplication.dbHelper = dbHelper;
    }
}
