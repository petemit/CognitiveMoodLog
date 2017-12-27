package com.mindbuilders.cognitivemoodlog;

import android.app.Application;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataBuffer;
import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import net.sqlcipher.database.SQLiteDatabase;
import static net.sqlcipher.database.SQLiteDatabase.loadLibs;


/**
 * Created by Peter on 12/4/2017.
 */

public class BaseApplication extends Application {
    private static CogMoodLogDatabaseHelper dbHelper;
    public static String passwordHash="";
    private static GoogleSignInClient googleSignInClient;
    private static GoogleSignInAccount googleSignInAccount;
    private static DriveClient driveClient;
    private static DriveResourceClient driveResourceClient;
    private static MetadataBuffer metaDataBuffer;

    public static GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    public static void setGoogleSignInClient(GoogleSignInClient googleSignInClient) {
        BaseApplication.googleSignInClient = googleSignInClient;
    }

    public static GoogleSignInAccount getGoogleSignInAccount() {
        return googleSignInAccount;
    }

    public static void setGoogleSignInAccount(GoogleSignInAccount googleSignInAccount) {
        BaseApplication.googleSignInAccount = googleSignInAccount;
    }

    public static DriveClient getDriveClient() {
        return driveClient;
    }

    public static void setDriveClient(DriveClient driveClient) {
        BaseApplication.driveClient = driveClient;
    }

    public static DriveResourceClient getDriveResourceClient() {
        return driveResourceClient;
    }

    public static void setDriveResourceClient(DriveResourceClient driveResourceClient) {
        BaseApplication.driveResourceClient = driveResourceClient;
    }

    public static MetadataBuffer getMetaDataBuffer() {
        return metaDataBuffer;
    }

    public static void setMetaDataBuffer(MetadataBuffer metaDataBuffer) {
        BaseApplication.metaDataBuffer = metaDataBuffer;
    }

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
