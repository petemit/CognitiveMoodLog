//package com.mindbuilders.cognitivemoodlog;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.DownloadManager;
//import android.content.Context;
//import android.content.DialogInterface;
//import net.sqlcipher.database.SQLiteDatabase;
//
//import android.content.pm.PackageManager;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.preference.CheckBoxPreference;
//import android.support.v7.preference.Preference;
//import android.support.v7.preference.PreferenceFragmentCompat;
//import android.com.mindbuilders.cognitivemoodlog.view.LayoutInflater;
//import android.com.mindbuilders.cognitivemoodlog.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;
//import com.mindbuilders.cognitivemoodlog.util.SqliteExporter;
//import com.mindbuilders.cognitivemoodlog.util.utilities;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import pub.devrel.easypermissions.EasyPermissions;
//
//import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
//import static android.content.Context.DOWNLOAD_SERVICE;
//import static com.mindbuilders.cognitivemoodlog.BaseApplication.REQUEST_PERMISSION_GET_ACCOUNTS;
//
//
///**
// * Created by Peter on 11/7/2017.
// */
//
//@SuppressLint("ValidFragment")
//public class SettingsFragment extends PreferenceFragmentCompat {
//
//    private String password = "";
//    private String key = "";
//    boolean changeResult = true;
//    LoadingIndicatorCallback callback;
//    Preference googleDrivePref;
//    CountDownLatch latch = new CountDownLatch(1);
//    /**
//     * Request code for google sign-in
//     */
//    protected static final int REQUEST_CODE_SIGN_IN = 0;
//
//    /**
//     * Request code for the Drive picker
//     */
//    protected static final int REQUEST_CODE_OPEN_ITEM = 1;
//
//
//    @SuppressLint("ValidFragment")
//    public SettingsFragment(LoadingIndicatorCallback callback) {
//        this.callback = callback;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        addPreferencesFromResource(R.xml.preferences);
//
//        final CheckBoxPreference passwordPref = (CheckBoxPreference) findPreference(getString(R.string.password_protect_key));
//        passwordPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(final Preference preference, Object o) {
//                final boolean result = (boolean) o;
//                if (result) {
//                    changeResult = true;
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
//                    View passwordView = LayoutInflater.from(getContext()).inflate(R.layout.password_prompt, null);
//                    alertDialogBuilder.setView(passwordView);
//                    final EditText userInput = (EditText) passwordView.findViewById(R.id.password_editTextDialogUserInput);
//                    alertDialogBuilder
//                            .setCancelable(false)
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    password = userInput.getText().toString();
//                                    boolean canClose = password.trim().isEmpty();
//                                    String key = utilities.getSha1Hex(password);
//                                    CogMoodLogDatabaseHelper.passwordProtectDb(result, preference.getContext(), key);
//
//
//                                    if (!canClose) {
//                                        Toast.makeText(getContext(),
//                                                "Database Encrypted and Password Protected!",
//                                                Toast.LENGTH_LONG).show();
//                                        changeResult = true;
//                                        passwordPref.setChecked(true);
//                                    } else {
//                                        Toast.makeText(getContext(),
//                                                "Password must not be empty and must contain non-space characters",
//                                                Toast.LENGTH_LONG).show();
//                                        changeResult = false;
//                                    }
//                                }
//                            })
//                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.cancel();
//                                    changeResult = false;
//                                }
//                            });
//
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//                    alertDialog.show();
//                    return false;
//                } else {
//                    CogMoodLogDatabaseHelper.passwordProtectDb(result, preference.getContext(), key);
//                    changeResult = true;
//                    passwordPref.setChecked(false);
//                }
//
//                return changeResult;
//            }
//        });
//
//        googleDrivePref = (Preference) findPreference(getString(R.string.drive_backup_key));
//        googleDrivePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                backupDb();
//                return true;
//            }
//        });
//
//
//        Preference RestoreFromBackup = (Preference) findPreference("restore_from_backup");
//        RestoreFromBackup.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                //signIn();
//                //MetadataBuffer buffer = getMetadataBufferList();
//                //              if (buffer != null && buffer.getCount() > 0) {
////                    Metadata md = buffer.get(0);
//                restoreBackup();
//                //  Toast.makeText(getContext(), "Backup Restored", Toast.LENGTH_SHORT).show();
//
//                //}
////                else {
////                    Toast.makeText(getContext(), "No backup found.", Toast.LENGTH_LONG).show();
////                    return true;
////                }
//                return true;
//            }
//        });
//
//
//        Preference delete = (Preference) findPreference("delete_files");
//        delete.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Delete Online Backup")
//                        .setMessage("Are you sure you want delete your online backup?  Data will be lost")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                deleteFiles();
//                            }
//                        })
//                        .setNegativeButton("No, I do not want to delete my online Backup", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                return;
//                            }
//                        }).show();
//
//                return true;
//            }
//        });
//
//        Preference download = (Preference) findPreference(getString(R.string.backup_to_csv_key));
//        download.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                AsyncTask<Activity, Void, Void> downloadTask = new downloadAsyncTask();
//                downloadTask.execute(getActivity());
//                return true;
//            }
//        });
//    }
//
//
//    private void restoreBackup() {
//        BaseApplication.initGoogleAccountCredential();
//        new RestoreAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.getActivity());
//
//    }
//
//
//    protected void deleteFiles() {
//        BaseApplication.initGoogleAccountCredential();
//        BaseApplication.getResultsFromApi(new WhatToDoTask(this.getActivity(), String.valueOf(OperationEnum.DELETEFILE), null));
//    }
//
//
//    protected void backupDb() {
//        BaseApplication.initGoogleAccountCredential();
//        new BackupAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.getActivity());
//    }
//
//
//    @Override
//    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//
//    }
//
//
//    public interface LoadingIndicatorCallback {
//        void showLoading();
//
//        void hideLoading();
//
//
//
//    }
//
//    class downloadAsyncTask extends AsyncTask<Activity, Void, Void> implements  MyProcessCallback {
//        Activity activity;
//        int currentStatus;
//        AlertDialog.Builder builder;
//        CountDownLatch latch = new CountDownLatch(1);
//        MyProcessCallback needACallback = this;
//        File file;
//        DownloadManager downloadManager;
//
//        @Override
//        public void finishedTask(int returnCode) {
//            latch.countDown();
//        }
//
//        @Override
//        protected Void doInBackground(Activity... params) {
//
//
//
//
//
//            if (params[0] != null) {
//                activity = params[0];
//                new BaseApplication.GetWritePermissionsAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,new WhatToDoTask(activity, "", this));
//                try {
//                    latch.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                SQLiteDatabase db = BaseApplication.getDbHelper().getReadableDatabase(BaseApplication.passwordHash);
//                downloadManager = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);
//                try {
//                    file = new File(SqliteExporter.export(db, activity.getApplicationContext()));
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            downloadManager.addCompletedDownload(file.getName(), file.getName(), true, "text/plain",file.getAbsolutePath(),file.length(),true);
//        }
//    }
//
//    class BackupAsyncTask extends AsyncTask<Activity, Void, Void> implements MyProcessCallback {
//        Activity activity;
//        int currentStatus;
//        AlertDialog.Builder builder;
//        CountDownLatch latch = new CountDownLatch(1);
//        MyProcessCallback needACallback = this;
//
//        @Override
//        protected Void doInBackground(Activity... params) {
//            if (params[0] != null) {
//                activity = params[0];
//                builder = new AlertDialog.Builder(activity);
//                BaseApplication.getResultsFromApi(new WhatToDoTask((activity), String.valueOf(OperationEnum.CHECKFORFILE), needACallback));
//                try {
//                    latch.await(10, TimeUnit.SECONDS);
//                    latch = new CountDownLatch(1);
//                    if (currentStatus == DriveReturnCodes.FOUNDFILE) {
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                builder.setTitle("Delete Existing Backup")
//                                        .setMessage("Existing DB Backup Found, do you want to Overwrite this DB?  Existing Data Will be Lost")
//                                        .setPositiveButton("Yes, Overwrite", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                BaseApplication.getResultsFromApi(new WhatToDoTask((activity), String.valueOf(OperationEnum.DELETEFILE), needACallback));
//                                            }
//                                        })
//                                        .setNegativeButton("No, do not Overwrite", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                latch.countDown();
//                                                return;
//                                            }
//                                        }).show();
//                            }
//                        });
//
//                        latch.await();
//                        latch = new CountDownLatch(1);
//                    }
//                    if (currentStatus == DriveReturnCodes.FILESDELETED || currentStatus == DriveReturnCodes.NOFILEFOUND) {
//                        BaseApplication.getResultsFromApi(new WhatToDoTask((activity), String.valueOf(OperationEnum.INSERTFILE), needACallback));
//                        latch.await(10, TimeUnit.SECONDS);
//                        if (currentStatus == DriveReturnCodes.DBBACKEDUP) {
//                            return null;
//                        }
//                    }
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//        @Override
//        public void finishedTask(int returnCode) {
//            currentStatus = returnCode;
//            latch.countDown();
//        }
//    }
//
//
//    class RestoreAsyncTask extends AsyncTask<Activity, Void, Void> implements MyProcessCallback {
//        Activity activity;
//        int currentStatus;
//        AlertDialog.Builder builder;
//        CountDownLatch latch = new CountDownLatch(1);
//        MyProcessCallback needACallback = this;
//
//        @Override
//        protected Void doInBackground(Activity... params) {
//            if (params[0] != null) {
//                activity = params[0];
//                builder = new AlertDialog.Builder(activity);
//                BaseApplication.getResultsFromApi(new WhatToDoTask((activity), String.valueOf(OperationEnum.CHECKFORFILE), needACallback));
//                try {
//                    latch.await(10, TimeUnit.SECONDS);
//                    latch = new CountDownLatch(1);
//                    if (currentStatus == DriveReturnCodes.FOUNDFILE) {
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                builder.setTitle("Found Existing Backup")
//                                        .setMessage("DB Backup Found on Your Google Drive, do you want to Overwrite your local DB?  Existing Data Will be Lost")
//                                        .setPositiveButton("Yes, Restore Backup", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                BaseApplication.getResultsFromApi(new WhatToDoTask((activity), String.valueOf(OperationEnum.DOWNLOADFILE), needACallback));
//                                            }
//                                        })
//                                        .setNegativeButton("No, keep Existing Data", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                latch.countDown();
//                                                return;
//                                            }
//                                        }).show();
//                            }
//                        });
//                        latch.await();
//                        latch = new CountDownLatch(1);
//                        if (currentStatus == DriveReturnCodes.BACKUPRESTORED) {
//                            activity.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(activity, "Restore Complete, restarting app", Toast.LENGTH_SHORT).show();
//                                    activity.finish();
//                                }
//                            });
//                        }
//
//                    }
//                    if (currentStatus == DriveReturnCodes.NOFILEFOUND) {
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                builder.setTitle("No Backup Found")
//                                        .setMessage("No DB Backup Found on Your Google Drive")
//                                        .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                            }
//                                        }).show();
//                            }
//                        });
//                    }
////                    if (currentStatus == DriveReturnCodes.FILESDELETED || currentStatus == DriveReturnCodes.NOFILEFOUND) {
////                        BaseApplication.getResultsFromApi(new WhatToDoTask((activity), String.valueOf(OperationEnum.INSERTFILE),needACallback));
////                        latch.await(10, TimeUnit.SECONDS);
////                        if (currentStatus == DriveReturnCodes.DBBACKEDUP) {
////                            return null;
////                        }
////                    }
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//        @Override
//        public void finishedTask(int returnCode) {
//            currentStatus = returnCode;
//            latch.countDown();
//        }
//    }
//
//
//}
