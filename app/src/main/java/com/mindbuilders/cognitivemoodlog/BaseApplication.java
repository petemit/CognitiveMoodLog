package com.mindbuilders.cognitivemoodlog;

import android.app.Application;

import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;

/**
 * Created by Peter on 12/4/2017.
 */

public class BaseApplication extends Application {
    private static CogMoodLogDatabaseHelper dbHelper;
    public static String passwordHash="";
    @Override
    public void onCreate() {
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
