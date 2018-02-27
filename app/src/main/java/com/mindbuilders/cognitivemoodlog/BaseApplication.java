package com.mindbuilders.cognitivemoodlog;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static net.sqlcipher.database.SQLiteDatabase.loadLibs;


/**
 * Created by Peter on 12/4/2017.
 */

public class BaseApplication extends Application implements EasyPermissions.PermissionCallbacks {
    public static Context myContext;

    private static GoogleAccountCredential driveCredential;
    private static CogMoodLogDatabaseHelper dbHelper;
    public static String passwordHash = "";
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    public static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {DriveScopes.DRIVE_METADATA_READONLY, DriveScopes.DRIVE_APPDATA};

    public static GoogleAccountCredential getDriveCredential() {
        return driveCredential;
    }

    public static void setDriveCredential(GoogleAccountCredential driveCredential) {
        BaseApplication.driveCredential = driveCredential;
    }

    @Override
    public void onCreate() {
        myContext = getApplicationContext();
        loadLibs(getApplicationContext());
        BaseApplication.setDbHelper(new CogMoodLogDatabaseHelper(getBaseContext()));
        super.onCreate();

        if (BuildConfig.DEBUG) {
            StethoEnabler.enable(this);
        }

    }

    public static void initGoogleAccountCredential() {
        setDriveCredential(GoogleAccountCredential.usingOAuth2(
                myContext, Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff()));
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private static void acquireGooglePlayServices(Activity activity) {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(activity.getBaseContext());
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(activity, connectionStatusCode);
        }
    }


    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     *
     * @param connectionStatusCode code describing the presence (or lack of)
     *                             Google Play Services on this device.
     */
    static void showGooglePlayServicesAvailabilityErrorDialog(Activity activity,
                                                              final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                activity,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }


    /**
     * Check that Google Play services APK is installed and up to date.
     *
     * @return true if Google Play Services is available and up to
     * date on this device; false otherwise.
     */
    private static boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(context);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Checks whether the device currently has a network connection.
     *
     * @return true if the device has a network connection, false otherwise.
     */
    private static boolean isDeviceOnline(Context context) {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static void getResultsFromApi(Activity activity) {
        if (!isGooglePlayServicesAvailable(activity.getBaseContext())) {
            acquireGooglePlayServices(activity);
        } else if (getDriveCredential().getSelectedAccountName() == null) {
            chooseAccount(activity);
        } else if (!isDeviceOnline(activity.getBaseContext())) {

        } else {
            new MakeRequestTask(getDriveCredential()).execute(activity);
        }
    }

    /**
     * An asynchronous task that handles the Drive API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private static class MakeRequestTask extends AsyncTask<Activity, Void, List<String>> {
        private com.google.api.services.drive.Drive mService = null;
        private Exception mLastError = null;
        private Activity activity;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.drive.Drive.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Cognitive Mood Log")
                    .build();
        }

        /**
         * Background task to call Drive API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Activity... params) {
            if (params[0] instanceof Activity) {
                this.activity = params[0];
            }
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of up to 10 file names and IDs.
         *
         * @return List of Strings describing files, or an empty list if no files
         * found.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // Get a list of up to 10 files.
            List<String> fileInfo = new ArrayList<String>();
            FileList files = mService.files().list()
                    .setSpaces("appDataFolder")
                    .setFields("nextPageToken, files(id, name)")
                    .setPageSize(10)
                    .execute();
            if (files != null) {
                for (File file : files.getFiles()) {
                   fileInfo.add(file.getName() + " " + file.getId());
                }
            }

            return fileInfo;
        }


        @Override
        protected void onPreExecute() {
//            mOutputText.setText("");
//            mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
//            mProgress.hide();
            if (output == null || output.size() == 0) {
//                mOutputText.setText("No results returned.");
            } else {
                output.add(0, "Data retrieved using the Drive API:");
                Toast.makeText(activity, output.toString(), Toast.LENGTH_SHORT).show();
//                mOutputText.setText(TextUtils.join("\n", output));
            }
        }

        @Override
        protected void onCancelled() {
            //  mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(activity,
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    activity.startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            REQUEST_AUTHORIZATION);
                } else {
                  //  mOutputText.setText("The following error occurred:\n"
                         //   + mLastError.getMessage());
                }
            } else {
               // mOutputText.setText("Request cancelled.");
            }
        }
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    public static void chooseAccount(Activity activity) {
        if (EasyPermissions.hasPermissions(
                activity.getBaseContext(), Manifest.permission.GET_ACCOUNTS)) {

            String accountName = activity.getPreferences(Context.MODE_PRIVATE).getString(PREF_ACCOUNT_NAME, null);

            if (accountName != null) {
                getDriveCredential().setSelectedAccountName(accountName);
                getResultsFromApi(activity);
            } else {
                // Start a dialog from which the user can choose an account
                activity.startActivityForResult(
                        getDriveCredential().newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    activity,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    public static CogMoodLogDatabaseHelper getDbHelper() {
        return dbHelper;
    }

    public static void setDbHelper(CogMoodLogDatabaseHelper dbHelper) {
        BaseApplication.dbHelper = dbHelper;
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }
}
