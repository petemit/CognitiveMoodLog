//package com.mindbuilders.cognitivemoodlog;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Application;
//import android.app.Dialog;
//import android.app.DownloadManager;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.support.annotation.NonNull;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.googleapis.batch.BatchRequest;
//import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
//import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
//import com.google.api.client.googleapis.json.GoogleJsonError;
//import com.google.api.client.http.FileContent;
//import com.google.api.client.http.HttpHeaders;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.util.ExponentialBackOff;
//import com.google.api.services.drive.DriveScopes;
//import com.google.api.services.drive.model.File;
//import com.google.api.services.drive.model.FileList;
//import com.google.api.services.drive.model.Permission;
//import com.mindbuilders.cognitivemoodlog.CmlDos.emotionobj;
//import com.mindbuilders.cognitivemoodlog.CmlDos.thought_cognitivedistortionobj;
//import com.mindbuilders.cognitivemoodlog.CmlDos.thoughtobj;
//import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;
//import com.mindbuilders.cognitivemoodlog.util.SqliteExporter;
//
//import net.sqlcipher.database.SQLiteDatabase;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//
//import pub.devrel.easypermissions.AfterPermissionGranted;
//import pub.devrel.easypermissions.EasyPermissions;
//
//import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
//import static net.sqlcipher.database.SQLiteDatabase.loadLibs;
//
//
///**
// * Created by Peter on 12/4/2017.
// */
//
//public class BaseApplication extends Application{
//    public static Context myContext;
//
//    private static GoogleAccountCredential driveCredential;
//    private static CogMoodLogDatabaseHelper dbHelper;
//    private static CountDownLatch latch;
//    public static String passwordHash = "";
//    static final int REQUEST_ACCOUNT_PICKER = 1000;
//    static final int REQUEST_AUTHORIZATION = 1001;
//    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
//    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
//    public static final String PREF_ACCOUNT_NAME = "accountName";
//    private static final String[] SCOPES = {DriveScopes.DRIVE_APPDATA};
//    public static List<thoughtobj> thoughtobjs;
//    public static List<emotionobj> emotionobjs;
//    public static List<thought_cognitivedistortionobj> thought_cognitivedistortionobjs;
//
//    public BaseApplication() {
//        setLatch(new CountDownLatch(1));
//    }
//
//    public static GoogleAccountCredential getDriveCredential() {
//        return driveCredential;
//    }
//
//    public static void setDriveCredential(GoogleAccountCredential driveCredential) {
//        BaseApplication.driveCredential = driveCredential;
//    }
//
//
//
//    @Override
//    public void onCreate() {
//        myContext = getApplicationContext();
//        loadLibs(getApplicationContext());
//        BaseApplication.setDbHelper(new CogMoodLogDatabaseHelper(getBaseContext()));
//        super.onCreate();
//
//        if (BuildConfig.DEBUG) {
//            StethoEnabler.enable(this);
//        }
//
//    }
//
//    public static void initGoogleAccountCredential() {
//        if (BaseApplication.getDriveCredential() == null ) {
//            setDriveCredential(GoogleAccountCredential.usingOAuth2(
//                    myContext, Arrays.asList(SCOPES))
//                    .setBackOff(new ExponentialBackOff()));
//        }
//    }
//
//    /**
//     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
//     * Play Services installation via a user dialog, if possible.
//     */
//    private static void acquireGooglePlayServices(WhatToDoTask task) {
//        Activity activity = task.getActivity();
//        if (activity == null) {
//            return;
//        }
//        GoogleApiAvailability apiAvailability =
//                GoogleApiAvailability.getInstance();
//        final int connectionStatusCode =
//                apiAvailability.isGooglePlayServicesAvailable(activity.getBaseContext());
//        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
//            showGooglePlayServicesAvailabilityErrorDialog(activity, connectionStatusCode);
//        }
//    }
//
//
//    /**
//     * Display an error dialog showing that Google Play Services is missing
//     * or out of date.
//     *
//     * @param connectionStatusCode code describing the presence (or lack of)
//     *                             Google Play Services on this device.
//     */
//    static void showGooglePlayServicesAvailabilityErrorDialog(Activity activity,
//                                                              final int connectionStatusCode) {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        Dialog dialog = apiAvailability.getErrorDialog(
//                activity,
//                connectionStatusCode,
//                REQUEST_GOOGLE_PLAY_SERVICES);
//        dialog.show();
//    }
//
//
//    /**
//     * Check that Google Play services APK is installed and up to date.
//     *
//     * @return true if Google Play Services is available and up to
//     * date on this device; false otherwise.
//     */
//    private static boolean isGooglePlayServicesAvailable(Context context) {
//        GoogleApiAvailability apiAvailability =
//                GoogleApiAvailability.getInstance();
//        final int connectionStatusCode =
//                apiAvailability.isGooglePlayServicesAvailable(context);
//        return connectionStatusCode == ConnectionResult.SUCCESS;
//    }
//
//    /**
//     * Checks whether the device currently has a network connection.
//     *
//     * @return true if the device has a network connection, false otherwise.
//     */
//    private static boolean isDeviceOnline(Context context) {
//        ConnectivityManager connMgr =
//                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        return (networkInfo != null && networkInfo.isConnected());
//    }
//
//    public static void getResultsFromApi(final WhatToDoTask task) {
//        if (task.getActivity() != null && task.getActivity() instanceof Activity)
//        if (!isGooglePlayServicesAvailable(task.getActivity().getBaseContext())) {
//            acquireGooglePlayServices(task);
//        } else if (getDriveCredential().getSelectedAccountName() == null) {
//            chooseAccount(task);
//        } else if (!isDeviceOnline(task.getActivity().getBaseContext())) {
//
//        } else {
//            if (task.getActivity() instanceof PreferenceActivity) {
//                task.getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ((PreferenceActivity) task.getActivity()).showLoading();
//                    }
//                });
//
//            }
//            new MakeRequestTask(getDriveCredential()).execute(task);
//        }
//    }
//
//    public static CountDownLatch getLatch() {
//        return latch;
//    }
//
//    public static void setLatch(CountDownLatch lat) {
//        latch = lat;
//    }
//
//    public List<thought_cognitivedistortionobj> getThought_cognitivedistortionobjs() {
//        return thought_cognitivedistortionobjs;
//    }
//
//    public void setThought_cognitivedistortionobjs(List<thought_cognitivedistortionobj> thought_cognitivedistortionobjs) {
//        this.thought_cognitivedistortionobjs = thought_cognitivedistortionobjs;
//    }
//
//    public List<thoughtobj> getThoughtobjs() {
//        return thoughtobjs;
//    }
//
//    public void setThoughtobjs(List<thoughtobj> thoughtobjs) {
//        this.thoughtobjs = thoughtobjs;
//    }
//
//    public List<emotionobj> getEmotionobjs() {
//        return emotionobjs;
//    }
//
//    public void setEmotionobjs(List<emotionobj> emotionobjs) {
//        this.emotionobjs = emotionobjs;
//    }
//
//    /**
//     * An asynchronous task that handles the Drive API call.
//     * Placing the API calls in their own task ensures the UI stays responsive.
//     */
//    private static class MakeRequestTask extends AsyncTask<WhatToDoTask, Void, Integer> {
//        private com.google.api.services.drive.Drive mService = null;
//        private Exception mLastError = null;
//        private Activity activity;
//        private WhatToDoTask task;
//
//
//        MakeRequestTask(GoogleAccountCredential credential) {
//            HttpTransport transport = AndroidHttp.newCompatibleTransport();
//            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//            mService = new com.google.api.services.drive.Drive.Builder(
//                    transport, jsonFactory, credential)
//                    .setApplicationName("Cognitive Mood Log")
//                    .build();
//        }
//
//        /**
//         * Background task to call Drive API.
//         *
//         * @param params no parameters needed for this task.
//         */
//        @Override
//        protected Integer doInBackground(WhatToDoTask... params) {
//            if (params[0] != null) {
//                if (params[0] instanceof  WhatToDoTask) {
//                    task = params[0];
//                    this.activity = task.getActivity();
//
//                }
//            }
//            else {
//                return null;
//            }
//            try {
//                switch(params[0].getOp()) {
//                    case "CHECKFORFILE":
//                        return checkForFile();
//                    case "DELETEFILE":
//                        return deleteFiles();
//                    case "DOWNLOADFILE":
//                        return downloadFile();
//                    case "INSERTFILE":
//                        return insertFile();
//                    default:
//                        return null;
//                }
//
//            } catch (Exception e) {
//                mLastError = e;
//                cancel(true);
//                return null;
//            }
//        }
//
//        /**
//         * Fetch a list of up to 10 file names and IDs.
//         *
//         * @return List of Strings describing files, or an empty list if no files
//         * found.
//         * @throws IOException
//         */
//        private Integer checkForFile() throws IOException {
//            // Get a list of up to 10 files.
//            FileList files = mService.files().list()
//                    .setSpaces("appDataFolder")
//                    .setFields("nextPageToken, files(id, name, trashed)")
//                    .setPageSize(10)
//                    .execute();
//            if (files != null) {
//                for (File file : files.getFiles()) {
//                    if (null == file.getTrashed() || !file.getTrashed()) {
//                        return DriveReturnCodes.FOUNDFILE;
//                     //   fileInfo.add(file.getName() + " " + file.getId());
//                    }
//                }
//                return DriveReturnCodes.NOFILEFOUND;
//            } else {
//                return DriveReturnCodes.EMPTYSET;
//            }
//        }
//
//        private Integer insertFile() throws IOException {
//
//            File fileMetaData = new File();
//            fileMetaData.setName(CogMoodLogDatabaseHelper.DATABASE_NAME);
//            fileMetaData.setParents(Collections.singletonList("appDataFolder"));
//            java.io.File dbFilePath = CogMoodLogDatabaseHelper.getDbFile(activity.getBaseContext());
//            FileContent mediaContent = new FileContent("application/x-sqlite3",dbFilePath);
//            mService.files().create(fileMetaData, mediaContent)
//                    .setFields("id")
//                    .execute();
//            return DriveReturnCodes.DBBACKEDUP;
//        }
//
//        private Integer downloadFile() throws IOException {
//
//            FileList files = mService.files().list()
//                    .setSpaces("appDataFolder")
//                    .setFields("nextPageToken, files(id, name, trashed)")
//                    .setPageSize(10)
//                    .execute();
//            if (files != null) {
//                ArrayList<File> notDeletedfiles = new ArrayList<>();
//                for (File f:files.getFiles()) {
//                    if (!f.getTrashed() && f.getName().equals(CogMoodLogDatabaseHelper.DATABASE_NAME)) {
//                        notDeletedfiles.add(f);
//                    }
//                }
//
//                if (notDeletedfiles.size() > 0 ) {
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    mService.files().get(notDeletedfiles.get(0).getId()).executeMediaAndDownloadTo(outputStream);
//                    CogMoodLogDatabaseHelper.restoreDb(myContext,outputStream);
//                    return DriveReturnCodes.BACKUPRESTORED;
//                }
//            }
//            return DriveReturnCodes.EMPTYSET;
//        }
//
//        private Integer deleteFiles() throws IOException {
//
//            FileList files = mService.files().list()
//                    .setSpaces("appDataFolder")
//                    .setFields("nextPageToken, files(id, name, trashed)")
//                    .setPageSize(10)
//                    .execute();
//            if (files != null) {
//                JsonBatchCallback<File> callback = new JsonBatchCallback<File>() {
//                    @Override
//                    public void onSuccess(File file,
//                                          HttpHeaders responseHeaders)
//                            throws IOException {
//                        System.out.println("Permission ID: " + file.getId());
//                    }
//
//                    @Override
//                    public void onFailure(GoogleJsonError e,
//                                          HttpHeaders responseHeaders)
//                            throws IOException {
//                        // Handle error
//                        System.err.println(e.getMessage());
//                    }
//                };
//
//
//                BatchRequest batch = mService.batch();
//                for (File file : files.getFiles()) {
//                    File newContent = new File();
//                    newContent.setTrashed(true);
//                    mService.files().update(file.getId(), newContent).queue(batch,callback);
//                 //  mService.files().delete(file.getId());
//                }
//                batch.execute();
//                return DriveReturnCodes.FILESDELETED;
//            }
//
//            return DriveReturnCodes.EMPTYSET;
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//
//        }
//
//        @Override
//        protected void onPostExecute(Integer output) {
//            if (activity instanceof PreferenceActivity) {
//                ((PreferenceActivity) activity).hideLoading();
//            }
//            if (output != null ) {
//                if (output == DriveReturnCodes.ERROR || output == DriveReturnCodes.EMPTYSET) {
//                    Toast.makeText(activity, DriveReturnCodes.getMessage(output), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(activity, DriveReturnCodes.getMessage(output), Toast.LENGTH_SHORT).show();
//                    activity = null;
//                }
//            }
//            if (task != null && task.getCallback() != null) {
//                task.getCallback().finishedTask(output);
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            //  mProgress.hide();
//            if (mLastError != null) {
//                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
//                    showGooglePlayServicesAvailabilityErrorDialog(activity,
//                            ((GooglePlayServicesAvailabilityIOException) mLastError)
//                                    .getConnectionStatusCode());
//                } else if (mLastError instanceof UserRecoverableAuthIOException) {
//                    activity.startActivityForResult(
//                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
//                            REQUEST_AUTHORIZATION);
//                } else {
//                  //  mOutputText.setText("The following error occurred:\n"
//                         //   + mLastError.getMessage());
//                }
//            } else {
//               // mOutputText.setText("Request cancelled.");
//            }
//        }
//    }
//
//    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
//    public static void chooseAccount(WhatToDoTask task) {
//        new GetDrivePermissionsAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,task);
//
//
//    }
//
//    public static class GetWritePermissionsAsyncTask extends  AsyncTask<WhatToDoTask, Void, Void> {
//        WhatToDoTask task;
//
//        @Override
//        protected Void doInBackground(WhatToDoTask... whatToDoTasks) {
//
//            if (whatToDoTasks != null) {
//                task = whatToDoTasks[0];
//            } else {
//                return null;
//            }
//            if (Build.VERSION.SDK_INT >= 23) {
//                if (task.getActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE)
//                        == PackageManager.PERMISSION_GRANTED) {
//
//                } else {
//                    EasyPermissions.requestPermissions(
//                            task.getActivity(),
//                            "This app needs to access your external storage to save your backup",
//                            1005,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                    try {
//                        BaseApplication.getLatch().await();
//                        BaseApplication.setLatch(new CountDownLatch(1));
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            task.getCallback().finishedTask(1005);
//            return null;
//        }
//    }
//
//    public static class GetDrivePermissionsAsyncTask extends  AsyncTask<WhatToDoTask, Void, Void> {
//        WhatToDoTask task;
//
//        @Override
//        protected Void doInBackground(WhatToDoTask... whatToDoTasks) {
//            if (whatToDoTasks != null) {
//                task = whatToDoTasks[0];
//            } else {
//                return null;
//            }
//            Activity activity = task.getActivity();
//            if (activity == null) {
//                return null;
//            }
//            if (EasyPermissions.hasPermissions(
//                    activity.getBaseContext(), Manifest.permission.GET_ACCOUNTS)) {
//
//                String accountName = activity.getPreferences(Context.MODE_PRIVATE).getString(PREF_ACCOUNT_NAME, null);
//
//                if (accountName != null) {
//                    getDriveCredential().setSelectedAccountName(accountName);
//                    getResultsFromApi(task);
//                } else {
//                    // Start a dialog from which the user can choose an account
//
//                    activity.startActivityForResult(
//                            getDriveCredential().newChooseAccountIntent().putExtra("op",task.getOp()),
//                            REQUEST_ACCOUNT_PICKER);
//                    try {
//                        BaseApplication.getLatch().await();
//                        BaseApplication.setLatch(new CountDownLatch(1));
//                        getResultsFromApi(task);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//
//
//                }
//            } else {
//                // Request the GET_ACCOUNTS permission via a user dialog
//                EasyPermissions.requestPermissions(
//                        activity,
//                        "This app needs to access your Google account (via Contacts).",
//                        REQUEST_PERMISSION_GET_ACCOUNTS,
//                        Manifest.permission.GET_ACCOUNTS);
//                try {
//                    BaseApplication.getLatch().await();
//                    BaseApplication.setLatch(new CountDownLatch(1));
//                    getResultsFromApi(task);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            return null;
//        }
//    }
//
//    public static CogMoodLogDatabaseHelper getDbHelper() {
//        return dbHelper;
//    }
//
//    public static void setDbHelper(CogMoodLogDatabaseHelper dbHelper) {
//        BaseApplication.dbHelper = dbHelper;
//    }
//
//}
