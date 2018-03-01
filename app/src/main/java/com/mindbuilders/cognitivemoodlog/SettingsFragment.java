package com.mindbuilders.cognitivemoodlog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;
import com.mindbuilders.cognitivemoodlog.util.utilities;

import java.util.concurrent.CountDownLatch;


/**
 * Created by Peter on 11/7/2017.
 */

@SuppressLint("ValidFragment")
public class SettingsFragment extends PreferenceFragmentCompat {

    private String password = "";
    private String key = "";
    boolean changeResult = true;
    LoadingIndicatorCallback callback;
    CheckBoxPreference googleDrivePref;
    CountDownLatch latch = new CountDownLatch(1);
    /**
     * Request code for google sign-in
     */
    protected static final int REQUEST_CODE_SIGN_IN = 0;

    /**
     * Request code for the Drive picker
     */
    protected static final int REQUEST_CODE_OPEN_ITEM = 1;


    @SuppressLint("ValidFragment")
    public SettingsFragment(LoadingIndicatorCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        final CheckBoxPreference passwordPref = (CheckBoxPreference) findPreference(getString(R.string.password_protect_key));
        passwordPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, Object o) {
                final boolean result = (boolean) o;
                if (result) {
                    changeResult = true;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    View passwordView = LayoutInflater.from(getContext()).inflate(R.layout.password_prompt, null);
                    alertDialogBuilder.setView(passwordView);
                    final EditText userInput = (EditText) passwordView.findViewById(R.id.password_editTextDialogUserInput);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    password = userInput.getText().toString();
                                    boolean canClose = password.trim().isEmpty();
                                    String key = utilities.getSha1Hex(password);
                                    CogMoodLogDatabaseHelper.passwordProtectDb(result, preference.getContext(), key);


                                    if (!canClose) {
                                        Toast.makeText(getContext(),
                                                "Database Encrypted and Password Protected!",
                                                Toast.LENGTH_LONG).show();
                                        changeResult = true;
                                        passwordPref.setChecked(true);
                                    } else {
                                        Toast.makeText(getContext(),
                                                "Password must not be empty and must contain non-space characters",
                                                Toast.LENGTH_LONG).show();
                                        changeResult = false;
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    changeResult = false;
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    return false;
                } else {
                    CogMoodLogDatabaseHelper.passwordProtectDb(result, preference.getContext(), key);
                    changeResult = true;
                    passwordPref.setChecked(false);
                }

                return changeResult;
            }
        });

        googleDrivePref = (CheckBoxPreference) findPreference(getString(R.string.drive_backup_key));
        googleDrivePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if ((boolean) newValue) {
                    signIn();
                    googleDrivePref.setChecked(true);
                } else {
                    googleDrivePref.setChecked(false);
                    // disable backup
                    signOut();
                }
                return false;
            }
        });


        Preference RestoreFromBackup = (Preference) findPreference("restore_from_backup");
        RestoreFromBackup.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //signIn();
                //MetadataBuffer buffer = getMetadataBufferList();
                //              if (buffer != null && buffer.getCount() > 0) {
//                    Metadata md = buffer.get(0);
                restoreBackup();
              //  Toast.makeText(getContext(), "Backup Restored", Toast.LENGTH_SHORT).show();

                //}
//                else {
//                    Toast.makeText(getContext(), "No backup found.", Toast.LENGTH_LONG).show();
//                    return true;
//                }
                return true;
            }
        });

        Preference delete = (Preference) findPreference("delete_files");
        delete.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                deleteFiles();
                return true;
            }
        });
    }
//
//    private MetadataBuffer getMetadataBufferList() {
//
//        BaseApplication.setMetaDataBuffer(null);
//        MetadataBuffer metadata = null;
//
////todo  I need this to use callbacks because I've got timing issues.
//        final Query query = new Query.Builder()
//                .addFilter(Filters.eq(SearchableField.TITLE, DATABASE_NAME)).build();
//
//        Task<DriveFolder> task = BaseApplication.getDriveResourceClient().getAppFolder();
//        task.addOnSuccessListener(new OnSuccessListener<DriveFolder>() {
//            @Override
//            public void onSuccess(DriveFolder driveFolder) {
//                BaseApplication.getDriveResourceClient().queryChildren(driveFolder, query);
//                Task<MetadataBuffer> queryTask = BaseApplication.getDriveResourceClient().queryChildren(driveFolder, query);
//
//                queryTask.addOnSuccessListener(new OnSuccessListener<MetadataBuffer>() {
//                    @Override
//                    public void onSuccess(MetadataBuffer buffer) {
//                        BaseApplication.setMetaDataBuffer(buffer);
//                    }
//                });
//            }
//        });
//        callback.showLoading();
//        new myAsyncTask().execute();
//
//        try {
//            latch.await(30, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return BaseApplication.getMetaDataBuffer();
//    }

//    private class myAsyncTask extends AsyncTask<Void, Void, Void> {
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (BaseApplication.getMetaDataBuffer() == null) {
//                    handler.postDelayed(this, 1000);
//                }
//            }
//        };
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            handler.postDelayed(runnable, 1000);
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            latch.countDown();
//            callback.hideLoading();
//        }
//    }

    private void restoreBackup() {
        BaseApplication.initGoogleAccountCredential();
        BaseApplication.getResultsFromApi(new WhatToDoTask(this.getActivity(), String.valueOf(OperationEnum.DOWNLOADFILE)));
//        final byte[] buffer = new byte[8 * 1024];
//        final File dbFile = new File((getContext().getDatabasePath("a").getParentFile()), DATABASE_NAME);
//
//        DriveFile file = getDriveFile(md);
//
//        Task<DriveContents> openTask = BaseApplication.getDriveResourceClient().openFile(file, DriveFile.MODE_READ_ONLY);
//        openTask.continueWithTask(new Continuation<DriveContents, Task<Void>>() {
//            @Override
//            public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
//                DriveContents contents = task.getResult();
//                FileOutputStream fos = null;
//                InputStream input = null;
//                try {
//                    input = contents.getInputStream();
//                    fos = new FileOutputStream(dbFile);
//                    int bytesRead;
//                    while ((bytesRead = input.read(buffer)) != -1) {
//                        fos.write(buffer, 0, bytesRead);
//                    }
//                } finally {
//                    if (fos != null) {
//                        fos.close();
//                    }
//                    if (input != null) {
//                        input.close();
//                    }
//                }
//                return null;
//            }
//        });
    }


    protected void deleteFiles() {
        BaseApplication.initGoogleAccountCredential();
        BaseApplication.getResultsFromApi(new WhatToDoTask(this.getActivity(), String.valueOf(OperationEnum.DELETEFILE)));
    }

    protected void signIn() {
        BaseApplication.initGoogleAccountCredential();
        BaseApplication.getResultsFromApi(new WhatToDoTask(this.getActivity(), String.valueOf(OperationEnum.CHECKFORFILE)));
//        Set<Scope> requiredScopes = new HashSet<>(2);
//            requiredScopes.add(Drive.SCOPE_APPFOLDER);
//            BaseApplication.setGoogleSignInAccount(GoogleSignIn.getLastSignedInAccount(this.getActivity()));
//            if (BaseApplication.getGoogleSignInAccount() != null &&
//                    BaseApplication.getGoogleSignInAccount().getGrantedScopes().containsAll(requiredScopes)) {
//                initializeDriveClient(BaseApplication.getGoogleSignInAccount());
//            } else {
//                GoogleSignInOptions signInOptions =
//                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                                .requestScopes(Drive.SCOPE_APPFOLDER)
//                                .build();
//                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this.getActivity(), signInOptions);
//                startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
//        }
    }


    protected void signOut() {
//        if (BaseApplication.getGoogleSignInClient() != null) {
//            BaseApplication.getGoogleSignInClient().signOut();
//        }
    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }


    public interface LoadingIndicatorCallback {
        void showLoading();

        void hideLoading();
    }


}
