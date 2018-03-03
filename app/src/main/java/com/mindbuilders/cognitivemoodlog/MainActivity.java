package com.mindbuilders.cognitivemoodlog;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseContract;
import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;
import com.mindbuilders.cognitivemoodlog.util.utilities;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {

    Button createNewLogEntryButton;
    Button explainCognitiveTherapyButton;
    Button openOldEntry;
    TextView qotd;
    static boolean FIRSTLOAD = false;
    Context context;

    private final Context mContext = getBaseContext();
    /* Class reference to help load the constructor on runtime */
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cognitive Mood Log");
//delete the DB on startup so we can make sure it's created right.
        //       getBaseContext().deleteDatabase("CognitiveMoodLog.db");


        setSupportActionBar(toolbar);
        //Fantastic way to browse your database when the app is running.
        // Stetho.initializeWithDefaults(this);
        if (!FIRSTLOAD) {

            try {
                try {
                    db = BaseApplication.getDbHelper().getReadableDatabase(BaseApplication.passwordHash);
                    Cursor cursor = db.rawQuery("select * from emotion", null);
                    if (cursor.getCount() < 1) {
                        FIRSTLOAD = true;
                    }
                } catch (SQLException s) {
                    FIRSTLOAD = true;
                    Log.i("mainactivity", "db doesn't exist");
                }
                } catch (SQLiteException e) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    View passwordView = LayoutInflater.from(this).inflate(R.layout.password_prompt, null);
                    alertDialogBuilder.setView(passwordView);

                    final EditText userInput = (EditText) passwordView.findViewById(R.id.password_editTextDialogUserInput);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String password = userInput.getText().toString();

                                    String key = utilities.getSha1Hex(password);

                                    BaseApplication.passwordHash = key;
                                    //test password

                                    try {
                                        db = BaseApplication.getDbHelper().getReadableDatabase(BaseApplication.passwordHash);
                                        Cursor cursor = db.rawQuery("select * from emotion", null);
                                    } catch (SQLiteException s) {
                                        Toast.makeText(getBaseContext(),
                                                "You must use the password you set up earlier, or delete everything and start over",
                                                Toast.LENGTH_LONG).show();
                                        finish();
                                    }




                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    Toast.makeText(getBaseContext(),
                                            "You must use the password you set up earlier, or delete everything and start over",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            })
                            .setNeutralButton("Delete Everything", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialogInterface.cancel();
                                    AlertDialog.Builder areYouSureDialogBuilder = new AlertDialog.Builder(context);
                                    View areYouSureView = LayoutInflater.from(context).inflate(R.layout.are_you_sure, null);
                                    areYouSureDialogBuilder.setView(areYouSureView);

                                    areYouSureDialogBuilder
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    CogMoodLogDatabaseHelper.deleteDatabase(context);
                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.cancel();
                                                    Toast.makeText(getBaseContext(),
                                                            "You must use the password you set up earlier, or delete everything and start over",
                                                            Toast.LENGTH_LONG).show();
                                                    finish();
                                                }
                                            });
                                    AlertDialog areYouSureAlertDialog = areYouSureDialogBuilder.create();
                                    areYouSureAlertDialog.show();

                                }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                }


        }
        if (FIRSTLOAD) {

            try {





        /* Use CogMoodLogDatabaseHelper to get access to a writable database */
                db = BaseApplication.getDbHelper().getWritableDatabase(BaseApplication.passwordHash);

                PopulateCogMoodLogDatabase(db);
                SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(context);
                sp.edit().putBoolean(getString(R.string.password_protect_key),false).commit();
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, PreferenceActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        if (id == R.id.action_privacy_policy) {
            String url = "https://sites.google.com/view/cognitivemoodlogprivacypolicy";
            Intent privacyPolicyIntent = new Intent(Intent.ACTION_VIEW);
            privacyPolicyIntent.setData(Uri.parse(url));
            startActivity(privacyPolicyIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void openCreateNewLogEntry(View view) {
        Intent intent = new Intent(this, CreateNewLogEntryActivity.class);
        //  EditText editText = (EditText) findViewById(R.id.editMessage1);
        //  String message = editText.getText().toString();
        // intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


    }

    public void OpenExplainCognitiveTherapy(View view) {
        Intent intent = new Intent(this, ExplainCognitiveTherapyActivity.class);
        //  EditText editText = (EditText) findViewById(R.id.editMessage1);
        //  String message = editText.getText().toString();
        // intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


    }

    public void openPreviousLogs(View view) {
        Intent intent = new Intent(this, OpenPreviousLogsActivity.class);
        startActivity(intent);


    }

    private void PopulateCogMoodLogDatabase(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select * from emotion", null);
        if (cursor.getCount() < 1) {

            String cognitivedistortioncsv = "undistort_table_cognitivedistortion.csv";
            String emotioncsv = "undistort_table_emotion.csv";
            String emotioncategorycsv = "undistort_table_emotioncategory.csv";
            String troubleshootingguidelinescsv = "undistort_table_troubleshootingguidelines.csv";
            AssetManager manager = getBaseContext().getAssets();
            InputStream inStream = null;

            //populate db from cognitivedistortion csv.
            //columns are name, description in that order

            try {
                inStream = manager.open(cognitivedistortioncsv);

            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
            String line = "";
            db.beginTransaction();
            try {
                while ((line = buffer.readLine()) != null) {
                    String[] columns = line.split(";");
                    if (columns.length != 3) {
                        continue;
                    }
                    ContentValues cv = new ContentValues();
                    cv.put(CogMoodLogDatabaseContract.cognitivedistortion.COLUMN_COGID, columns[0].trim());
                    cv.put(CogMoodLogDatabaseContract.cognitivedistortion.COLUMN_NAME, columns[1].trim());
                    cv.put(CogMoodLogDatabaseContract.cognitivedistortion.COLUMN_DESCRIPTION, columns[2].trim());
                    db.insert(CogMoodLogDatabaseContract.cognitivedistortion.TABLE_NAME, null, cv);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.setTransactionSuccessful();
            db.endTransaction();


            //populate db from emotion csv.
            //columns are emotioncategory_id, name in that order

            try {
                inStream = manager.open(emotioncsv);
                buffer.close();

            } catch (IOException e) {

                e.printStackTrace();
            }

            buffer = new BufferedReader(new InputStreamReader(inStream));
            line = "";
            db.beginTransaction();
            try {
                while ((line = buffer.readLine()) != null) {
                    String[] columns = line.split(";");
                    if (columns.length != 3) {

                        continue;
                    }
                    ContentValues cv = new ContentValues();
                    cv.put(CogMoodLogDatabaseContract.emotion.COLUMN_EMOID, columns[0].trim());
                    cv.put(CogMoodLogDatabaseContract.emotion.COLUMN_EMOTIONCATEGORY_ID, columns[1].trim());
                    cv.put(CogMoodLogDatabaseContract.emotion.COLUMN_NAME, columns[2].trim());
                    db.insert(CogMoodLogDatabaseContract.emotion.TABLE_NAME, null, cv);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.setTransactionSuccessful();
            db.endTransaction();

            //populate db from emotioncategory csv.
            //columns are just "name"

            try {
                buffer.close();
                inStream = manager.open(emotioncategorycsv);

            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer = new BufferedReader(new InputStreamReader(inStream));
            line = "";
            db.beginTransaction();
            try {
                while ((line = buffer.readLine()) != null) {
                    String[] columns = line.split(";");
                    if (columns.length != 2) {

                        continue;
                    }
                    ContentValues cv = new ContentValues();
                    cv.put(CogMoodLogDatabaseContract.emotioncategory.COLUMN_EMOCATID, columns[0].trim());
                    cv.put(CogMoodLogDatabaseContract.emotioncategory.COLUMN_NAME, columns[1].trim());
                    db.insert(CogMoodLogDatabaseContract.emotioncategory.TABLE_NAME, null, cv);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.setTransactionSuccessful();
            db.endTransaction();

            //populate db from troubleshootingguidelines csv.
            //columns are question, explanation in that order

            try {
                inStream = manager.open(troubleshootingguidelinescsv);

            } catch (IOException e) {
                e.printStackTrace();
            }

            buffer = new BufferedReader(new InputStreamReader(inStream));
            line = "";
            db.beginTransaction();
            try {
                while ((line = buffer.readLine()) != null) {
                    String[] columns = line.split(";");
                    if (columns.length != 3) {

                        continue;
                    }
                    ContentValues cv = new ContentValues();
                    cv.put(CogMoodLogDatabaseContract.troubleshootingguidelines.COLUMN_TROUBLESHOOTID, columns[0].trim());
                    cv.put(CogMoodLogDatabaseContract.troubleshootingguidelines.COLUMN_QUESTION, columns[1].trim());
                    cv.put(CogMoodLogDatabaseContract.troubleshootingguidelines.COLUMN_EXPLANATION, columns[2].trim());
                    db.insert(CogMoodLogDatabaseContract.troubleshootingguidelines.TABLE_NAME, null, cv);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    @Override
    protected void onDestroy() {
        BaseApplication.getDbHelper().close();

        super.onDestroy();
    }


}
