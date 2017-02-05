package com.mindbuilders.cognitivemoodlog;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.facebook.stetho.Stetho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button createNewLogEntryButton;
    Button explainCognitiveTherapyButton;
    Button openOldEntry;
    TextView qotd;
    static boolean FIRSTLOAD=false;

    private final Context mContext =getBaseContext();
    /* Class reference to help load the constructor on runtime */
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cognitive Mood Log");
//delete the DB on startup so we can make sure it's created right.
 //       getBaseContext().deleteDatabase("CognitiveMoodLog.db");

        dbHelper =new CogMoodLogDatabaseHelper(getBaseContext());
        setSupportActionBar(toolbar);
        //Fantastic way to browse your database when the app is running.
       // Stetho.initializeWithDefaults(this);
        if (!FIRSTLOAD){

            try {

                db=dbHelper.getReadableDatabase();
                Cursor cursor=db.rawQuery("select * from emotion",null);
                if(cursor.getCount()<1){
                    FIRSTLOAD=true;
                }
            }
            catch (SQLException s){
                FIRSTLOAD=true;
                Log.i("mainactivity","db doesn't exist");
            }
        }
if (FIRSTLOAD){

    try {





        /* Use CogMoodLogDatabaseHelper to get access to a writable database */
        db = dbHelper.getWritableDatabase();

        PopulateCogMoodLogDatabase(db);
        db.close();



    }
    catch (Exception e)
    {e.printStackTrace();}

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

     /*   //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }


    public void openCreateNewLogEntry(View view) {
        Intent intent = new Intent(this, CreateNewLogEntry.class);
      //  EditText editText = (EditText) findViewById(R.id.editMessage1);
      //  String message = editText.getText().toString();
       // intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


    }

    public void OpenExplainCognitiveTherapy(View view) {
        Intent intent = new Intent(this, ExplainCognitiveTherapy.class);
        //  EditText editText = (EditText) findViewById(R.id.editMessage1);
        //  String message = editText.getText().toString();
        // intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


    }

    public void openPreviousLogs(View view) {
        Intent intent = new Intent(this, OpenPreviousLogs.class);
        startActivity(intent);


    }

    private void PopulateCogMoodLogDatabase(SQLiteDatabase db)
    {
        Cursor cursor=db.rawQuery("select * from emotion",null);
        if(cursor.getCount()<1) {

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
    protected void onDestroy(){
        dbHelper.close();

        super.onDestroy();
    }


}
