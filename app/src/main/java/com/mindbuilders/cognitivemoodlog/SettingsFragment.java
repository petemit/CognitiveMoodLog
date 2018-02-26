package com.mindbuilders.cognitivemoodlog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;
import com.mindbuilders.cognitivemoodlog.util.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.tasks.Tasks.await;
import static com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper.DATABASE_NAME;


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
    protected static final int REQUEST_CODE_SIGN_IN_AND_BACKUP = 100;


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
                    callback.showLoading();
                    new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected Void doInBackground(Void... voids) {
                            signIn(true);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            googleDrivePref.setChecked(true);
                            callback.hideLoading();
                        }
                    }.execute();


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
                callback.showLoading();

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        signIn(false);

                        try {
                            latch.await(20,TimeUnit.SECONDS);
                            latch = new CountDownLatch(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getMetadataBufferList();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Toast.makeText(getContext(), "Signed in", Toast.LENGTH_SHORT).show();
                        try {
                            latch.await(20,TimeUnit.SECONDS);
                            latch = new CountDownLatch(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        MetadataBuffer buffer = BaseApplication.getMetaDataBuffer();
                        if (buffer != null && buffer.getCount() > 0) {
                            Metadata md = buffer.get(0);
                            restoreBackup((md));
                            Toast.makeText(getContext(), "Backup Restored", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), "No backup found.", Toast.LENGTH_LONG).show();
                        }

                        callback.hideLoading();



                    }
                }.execute();

                return true;
            }
        });
    }

    private MetadataBuffer getMetadataBufferList() {

        BaseApplication.getDriveClient().requestSync();
        BaseApplication.setMetaDataBuffer(null);

//todo  I need this to use callbacks because I've got timing issues.
        final Query query = new Query.Builder()
                .addFilter(Filters.eq(SearchableField.TITLE, DATABASE_NAME)).build();

                Log.d("blah",BaseApplication.getDriveResourceClient().toString());

                Task<DriveFolder> task = BaseApplication.getDriveResourceClient().getAppFolder();
                try {
                    Tasks.await(task);
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                DriveFolder driveFolder = task.getResult();
                Task<MetadataBuffer> md2 =BaseApplication.getDriveResourceClient().listChildren(driveFolder);
                Task<Metadata> md4 =BaseApplication.getDriveResourceClient().getMetadata(driveFolder);

                try {
                    Tasks.await(md2);
                    Tasks.await(md4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                md2.getResult();
        MetadataBuffer d = md2.getResult();
        Metadata m4r = md4.getResult();
        Metadata m;

        BaseApplication.getDriveResourceClient().queryChildren(driveFolder, query);
                Task<MetadataBuffer> queryTask = BaseApplication.getDriveResourceClient().queryChildren(task.getResult(), query);
                try {
                    Tasks.await(queryTask);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MetadataBuffer buffer = queryTask.getResult();

                BaseApplication.setMetaDataBuffer(buffer);

                if (latch.getCount() > 0) {
                    latch.countDown();
                }

        return BaseApplication.getMetaDataBuffer();
    }


    private void restoreBackup(Metadata md) {
        final byte[] buffer = new byte[8 * 1024];
        final File dbFile = new File((getContext().getDatabasePath("a").getParentFile()), DATABASE_NAME);

        DriveFile file = getDriveFile(md);

        Task<DriveContents> openTask = BaseApplication.getDriveResourceClient().openFile(file, DriveFile.MODE_READ_ONLY);
        openTask.continueWithTask(new Continuation<DriveContents, Task<Void>>() {
            @Override
            public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
                DriveContents contents = task.getResult();
                FileOutputStream fos = null;
                InputStream input = null;
                try {
                    input = contents.getInputStream();
                    fos = new FileOutputStream(dbFile);
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                }
                return null;
            }
        });
    }

    private DriveResource getDriveResource(Metadata md) {
        if (md != null && md instanceof DriveResource) {
            return md.getDriveId().asDriveResource();

        }
        return null;
    }

    private DriveFile getDriveFile(Metadata md) {
        if (md != null) {
            return md.getDriveId().asDriveFile();
        }
        return null;
    }

    private void deleteAllBackups(MetadataBuffer buffer) {
        for (Metadata md : buffer) {
            BaseApplication.getDriveResourceClient().delete(getDriveResource(md));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN_AND_BACKUP:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(getContext(), "Must be signed in correctly to Google " +
                                    "to backup data. Do you have a Google account?",
                            Toast.LENGTH_LONG).show();
                    googleDrivePref.setChecked(false);
                    return;
                }


                Task<GoogleSignInAccount> getAccountTask =
                        GoogleSignIn.getSignedInAccountFromIntent(data);
                if (getAccountTask.isSuccessful()) {
                    BaseApplication.setGoogleSignInAccount(getAccountTask.getResult());
                    initializeDriveClient(BaseApplication.getGoogleSignInAccount());
                    CogMoodLogDatabaseHelper.backupDb(getContext(), BaseApplication.getDriveResourceClient());
                    googleDrivePref.setChecked(true);
                } else {
                    Log.e("settingsfragment", "Sign-in failed.");
                    return;
                }
                break;
            case REQUEST_CODE_SIGN_IN:
                Task<GoogleSignInAccount> getAccountTaskSignIn =
                        GoogleSignIn.getSignedInAccountFromIntent(data);
                if (getAccountTaskSignIn.isSuccessful()) {
                    BaseApplication.setGoogleSignInAccount(getAccountTaskSignIn.getResult());
                    initializeDriveClient(BaseApplication.getGoogleSignInAccount());
                    latch.countDown();
                } else {
                    Log.e("settingsfragment", "Sign-in failed.");
                    return;
                }

                break;
        }
        //Example has this twice, I'm guessing it's a mistake: super.onActivityResult(requestCode, resultCode, data);
    }

    protected void signIn(boolean backup) {
        boolean signedIn = false;
        Set<Scope> requiredScopes = new HashSet<>(2);
        requiredScopes.add(Drive.SCOPE_APPFOLDER);
        //BaseApplication.setGoogleSignInAccount(GoogleSignIn.getLastSignedInAccount(this.getActivity()));
        if (BaseApplication.getGoogleSignInAccount() != null &&
                BaseApplication.getGoogleSignInAccount().getGrantedScopes().containsAll(requiredScopes)) {
            initializeDriveClient(BaseApplication.getGoogleSignInAccount());
            signedIn = true;
            latch.countDown();
        } else {
            GoogleSignInOptions signInOptions =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestScopes(Drive.SCOPE_APPFOLDER)
                            .build();
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this.getActivity(), signInOptions);
            if (backup) {
                startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN_AND_BACKUP);
            } else {
                startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
            }
        }
        if (signedIn && backup) {
            CogMoodLogDatabaseHelper.backupDb(getContext(), BaseApplication.getDriveResourceClient());
        }


    }


    protected void signOut() {
        if (BaseApplication.getGoogleSignInClient() != null) {
            BaseApplication.getGoogleSignInClient().signOut();
        }
    }


    /**
     * Continues the sign-in process, initializing the Drive clients with the current
     * user's account.
     */
    private void initializeDriveClient(GoogleSignInAccount signInAccount) {

        BaseApplication.setDriveClient(Drive.getDriveClient(getContext(), signInAccount));
        BaseApplication.setDriveResourceClient(Drive.getDriveResourceClient(getContext(),
                signInAccount));

        //Then do something

    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }

    private void updateViewWithGoogleSignInAccountTask(Task<GoogleSignInAccount> task, final Context context) {
        Log.i("utils", "Update view with sign in account task");
        task.addOnSuccessListener(
                new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        Log.i("utils", "Sign in success");
                        // Build a drive client.
                        BaseApplication.setDriveClient(Drive.getDriveClient(context, googleSignInAccount));
                        // Build a drive resource client.
                        BaseApplication.setDriveResourceClient(Drive.getDriveResourceClient(context,
                                googleSignInAccount));
                        // Start camera.
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("utils", "Sign in failed", e);
                            }
                        });
    }

    public interface LoadingIndicatorCallback {
        void showLoading();

        void hideLoading();
    }


}
