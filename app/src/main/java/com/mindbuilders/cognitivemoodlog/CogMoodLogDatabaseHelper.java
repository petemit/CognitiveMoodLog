package com.mindbuilders.cognitivemoodlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mindbuilders.cognitivemoodlog.CogMoodLogDatabaseContract.*;

import java.lang.reflect.Array;
import java.security.Timestamp;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Peter on 12/30/2016.
 */

public class CogMoodLogDatabaseHelper extends SQLiteOpenHelper{
    public final static String DATABASE_NAME="CognitiveMoodLog.db";
    public final static int DATABASE_VERSION=1;

    private Cursor cursor;
    SQLiteDatabase db;
    CogMoodLogDatabaseHelper dbHelper;

    public CogMoodLogDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.rawQuery("select * from logentry", null);
        }
        catch (SQLException s) {
            Log.i("dbhelper", "creating new db");
            createDb(db);
        }

    }

    public void createDb(SQLiteDatabase db)
    {
        final String SQL_CREATE_COGNITIVEDISTORTION_TABLE =
                "CREATE TABLE " + cognitivedistortion.TABLE_NAME +" (" +
                        // cognitivedistortion._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        cognitivedistortion.COLUMN_DESCRIPTION + " TEXT, " +
                        cognitivedistortion.COLUMN_NAME + " TEXT " +
                        ");";
        db.execSQL(SQL_CREATE_COGNITIVEDISTORTION_TABLE);

        final String SQL_CREATE_EMOTION_TABLE =
                "CREATE TABLE " + emotion.TABLE_NAME +" (" +
                        //   emotion._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        emotion.COLUMN_EMOTIONCATEGORY_ID + " INTEGER, " +
                        emotion.COLUMN_NAME + " TEXT " +
                        ");";
        db.execSQL(SQL_CREATE_EMOTION_TABLE);

        final String SQL_CREATE_EMOTION_LOGENTRY_BELIEF_TABLE =
                "CREATE TABLE " + emotion_logentry_belief.TABLE_NAME +" (" +
                        //        emotion_logentry_belief._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        emotion_logentry_belief.COLUMN_EMOTION_ID + " INTEGER, " +
                        emotion_logentry_belief.COLUMN_LOGENTRY_ID + " INTEGER, " +
                        emotion_logentry_belief.COLUMN_BELIEFBEFORE + " INTEGER, " +
                        emotion_logentry_belief.COLUMN_BELIEFAFTER + " INTEGER " +
                        ");";
        db.execSQL(SQL_CREATE_EMOTION_LOGENTRY_BELIEF_TABLE);

        final String SQL_CREATE_EMOTIONCATEGORY_TABLE =
                "CREATE TABLE " + emotioncategory.TABLE_NAME +" (" +
                        //      emotioncategory._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        emotioncategory.COLUMN_NAME + " TEXT " +
                        ");";
        db.execSQL(SQL_CREATE_EMOTIONCATEGORY_TABLE);

        final String SQL_CREATE_LOGENTRY_TABLE =
                "CREATE TABLE " + logentry.TABLE_NAME +" (" +
                        //    logentry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        logentry.COLUMN_LOGENTRY + " TEXT, " +
                        logentry.COLUMN_USER_ID+ " INTEGER, " +
                        logentry.COLUMN_ISFINISHED + " BOOLEAN, " +
                        logentry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                        ");";
        db.execSQL(SQL_CREATE_LOGENTRY_TABLE);

        final String SQL_CREATE_THOUGHT_TABLE =
                "CREATE TABLE " + thought.TABLE_NAME +" (" +
                        //  thought._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        thought.COLUMN_NEGATIVETHOUGHT + " TEXT, " +
                        thought.COLUMN_POSITIVETHOUGHT + " TEXT, " +
                        thought.COLUMN_LOGENTRY_ID + " INTEGER, " +
                        thought.COLUMN_NEGATIVEBELIEFBEFORE + " INTEGER, " +
                        thought.COLUMN_NEGATIVEBELIEFAFTER + " INTEGER, " +
                        thought.COLUMN_POSITIVEBELIEFBEFORE + " INTEGER, " +
                        thought.COLUMN_POSITIVEBELIEFAFTER + " INTEGER " +
                        ");";
        db.execSQL(SQL_CREATE_THOUGHT_TABLE);

        final String SQL_CREATE_THOUGHT_COGNITIVEDISTORTION_TABLE =
                "CREATE TABLE " + thought_cognitivedistortion.TABLE_NAME +" (" +
                        //  thought_cognitivedistortion._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        thought_cognitivedistortion.COLUMN_THOUGHT_ID + " INTEGER, " +
                        thought_cognitivedistortion.COLUMN_COGNITIVEDISTORTION_ID + " INTEGER " +
                        ");";
        db.execSQL(SQL_CREATE_THOUGHT_COGNITIVEDISTORTION_TABLE);

        final String SQL_CREATE_TROUBLESHOOTINGGUIDELINES_TABLE =
                "CREATE TABLE " + troubleshootingguidelines.TABLE_NAME +" (" +
                        //  troubleshootingguidelines._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        troubleshootingguidelines.COLUMN_QUESTION + " TEXT, " +
                        troubleshootingguidelines.COLUMN_EXPLANATION + " TEXT " +
                        ");";
        db.execSQL(SQL_CREATE_TROUBLESHOOTINGGUIDELINES_TABLE);

    }

    public String saveLogEntry(List<emotionobj> emotionobjList,List<thought_cognitivedistortionobj> thought_cognitivedistortionobjList ,List<thoughtobj> thoughtobjList, String situation) {

        db = this.getWritableDatabase();
        //Input the situation
        ContentValues cv = new ContentValues();
        cv.put(logentry.COLUMN_LOGENTRY,situation.trim());
        cv.put(logentry.COLUMN_TIMESTAMP,getDateTime());
        long logid=db.insert(logentry.TABLE_NAME,null,cv);

        //input the thoughts associated
        for (thoughtobj tob : thoughtobjList){
            ContentValues tobcv = new ContentValues();
            tobcv.put(thought.COLUMN_LOGENTRY_ID,logid);
            tobcv.put(thought.COLUMN_NEGATIVEBELIEFAFTER,tob.getNegativebeliefAfter());
            tobcv.put(thought.COLUMN_NEGATIVEBELIEFBEFORE,tob.getNegativebeliefBefore());
            tobcv.put(thought.COLUMN_NEGATIVETHOUGHT,tob.getNegativethought());
            tobcv.put(thought.COLUMN_POSITIVEBELIEFBEFORE,tob.getPositivebeliefbefore());
            tobcv.put(thought.COLUMN_POSITIVETHOUGHT,tob.getPositivethought());
            //add the thought to the db
            long tobDbId= db.insert(thought.TABLE_NAME,null,tobcv);

            //add the cognitive distortion linked to the thought

            for(thought_cognitivedistortionobj tcdo:thought_cognitivedistortionobjList){
                if (tcdo.getThoughtid()==tob.getId())
                {
                    ContentValues tcdo_cv = new ContentValues();
                    tcdo_cv.put(thought_cognitivedistortion.COLUMN_COGNITIVEDISTORTION_ID
                            ,tcdo.getCognitivedistortionid());
                    tcdo_cv.put(thought_cognitivedistortion.COLUMN_THOUGHT_ID,tobDbId);
                    db.insert(thought_cognitivedistortion.TABLE_NAME,null,tcdo_cv);
                }
            }

        }//end tob for
        for (emotionobj emo : emotionobjList){
            ContentValues emocv=new ContentValues();
            emocv.put(emotion_logentry_belief.COLUMN_LOGENTRY_ID,logid);
            emocv.put(emotion_logentry_belief.COLUMN_BELIEFAFTER,emo.getFeelingStrengthBefore());
            emocv.put(emotion_logentry_belief.COLUMN_BELIEFBEFORE,emo.getGetFeelingstrengthAfter());
            emocv.put(emotion_logentry_belief.COLUMN_EMOTION_ID,emo.getId());
            db.insert(emotion_logentry_belief.TABLE_NAME,null,emocv);
        }
        return null;
    }

    public List<CognitiveDistortionobj> getCognitiveDistortionNameList(){
        List<CognitiveDistortionobj> cogobjs=new ArrayList<CognitiveDistortionobj>();
        /* Use CogMoodLogDatabaseHelper to get access to a readable database */
        db = this.getReadableDatabase();
        //   db=dbHelper.getReadableDatabase();
        String[] projection = {
                "rowid",
                CogMoodLogDatabaseContract.cognitivedistortion.COLUMN_NAME,
                CogMoodLogDatabaseContract.cognitivedistortion.COLUMN_DESCRIPTION,
        };

        String sortOrder =
                CogMoodLogDatabaseContract.cognitivedistortion.COLUMN_NAME + " DESC";

        Cursor cursor = db.query(
                CogMoodLogDatabaseContract.cognitivedistortion.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        //Populate the name of the list

        //TODO make this a string resource... maybe make a cleaner way to do this?
        CognitiveDistortionobj firstobj =new CognitiveDistortionobj();
        firstobj.setId(-1);
        firstobj.setDescription("Please select a cognitive distortion");
        firstobj.setName("----");
        cogobjs.add(firstobj);
        while (cursor.moveToNext()) {
            CognitiveDistortionobj obj =new CognitiveDistortionobj();
            obj.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("rowid"))));
            obj.setName(cursor.getString(cursor.getColumnIndex("name")));
            obj.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            cogobjs.add(obj);
        }
        db.close();

        return cogobjs;
    }

    public List getLogEntryList(){
        List<logentryobj> logobjs=new ArrayList<logentryobj>();
        /* Use CogMoodLogDatabaseHelper to get access to a readable database */
        db = this.getReadableDatabase();
        //   db=dbHelper.getReadableDatabase();
        String[] projection = {
                "rowid",
                logentry.COLUMN_TIMESTAMP,
                logentry.COLUMN_LOGENTRY
        };

        String sortOrder =
                logentry.COLUMN_TIMESTAMP + " DESC";

        Cursor cursor = db.query(
                logentry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        //Populate the name of the list

        //TODO 2nd time I'm doing this.  make this a string resource... maybe make a cleaner way to do this?
        logentryobj firstobj =new logentryobj();
        firstobj.setLogid(-1);
        firstobj.setSituation("Please select a previous log entry");
        firstobj.setTimestamp("");
        logobjs.add(firstobj);
        while (cursor.moveToNext()) {
            logentryobj obj =new logentryobj();
            obj.setLogid(Integer.parseInt(cursor.getString(cursor.getColumnIndex("rowid"))));
            obj.setSituation(cursor.getString(cursor.getColumnIndex("logentry")));
            obj.setTimestamp(cursor.getString(cursor.getColumnIndex("timestamp")));
            logobjs.add(obj);
        }
        db.close();

        return logobjs;
    }

    public List<troubleshootingobj> getTroubleshootingObj(){
        ArrayList<troubleshootingobj> troubleshootingList=new ArrayList<troubleshootingobj>();

        db=this.getReadableDatabase();

        String[] projection = {
                "rowid",
                troubleshootingguidelines.COLUMN_EXPLANATION,
                troubleshootingguidelines.COLUMN_QUESTION
        };
        String sortOrder =
                troubleshootingguidelines.COLUMN_QUESTION + " DESC";
        Cursor cursor = db.query(
                troubleshootingguidelines.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder // The sort order
        );
        while (cursor.moveToNext()) {
            troubleshootingobj obj = new troubleshootingobj();

            obj.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    "rowid"
            ))));
            obj.setAnswer(cursor.getString(cursor.getColumnIndex(
                    troubleshootingguidelines.COLUMN_EXPLANATION
            )));

            obj.setQuestion(cursor.getString(cursor.getColumnIndex(
                    troubleshootingguidelines.COLUMN_QUESTION
            )));
        troubleshootingList.add(obj);
        }
        return troubleshootingList;
    }

    public List<emotionobj> getEmotionObjListByLogid(int logid){
        ArrayList<emotionobj> emotionobjList=new ArrayList<emotionobj>();

        db = this.getReadableDatabase();


        String[] projection = {
                "rowid",
                emotion_logentry_belief.COLUMN_BELIEFBEFORE,
                emotion_logentry_belief.COLUMN_BELIEFAFTER,
                emotion_logentry_belief.COLUMN_EMOTION_ID,
                emotion_logentry_belief.COLUMN_LOGENTRY_ID,
        };
        String [] whereargs={Integer.toString(logid)};

        Cursor cursor = db.query(
                emotion_logentry_belief.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                emotion_logentry_belief.COLUMN_LOGENTRY_ID+"=?",                                // The columns for the WHERE clause
                whereargs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null // The sort order
        );
        while (cursor.moveToNext()) {
            emotionobj obj =new emotionobj();

            obj.setGetFeelingstrengthAfter(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    emotion_logentry_belief.COLUMN_BELIEFAFTER
            ))));
            obj.setFeelingstrengthBefore(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    emotion_logentry_belief.COLUMN_BELIEFBEFORE
            ))));

            obj.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    emotion_logentry_belief.COLUMN_EMOTION_ID
            ))));
            obj.setLogentryid(Integer.parseInt(cursor.getString(cursor.getColumnIndex
                    (emotion_logentry_belief.COLUMN_LOGENTRY_ID))));

            //Now get the emotion names


            String[] secondprojection = {
                    "rowid",
                    emotion.COLUMN_NAME,
                    emotion.COLUMN_EMOTIONCATEGORY_ID,
            };

            String sortOrder =
                    "rowid"+ " DESC";
            String [] secondwhereargs={Integer.toString(obj.getId())};
            Cursor secondcursor = db.query(
                    emotion.TABLE_NAME,                     // The table to query
                    secondprojection,                               // The columns to return
                    "rowid"+"=?",                                // The columns for the WHERE clause
                    secondwhereargs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder                                 // The sort order
            );
            //Populate the name of the list

            while (secondcursor.moveToNext()) {
                obj.setName(secondcursor.getString(secondcursor.getColumnIndex(
                        emotion.COLUMN_NAME
                )));
                obj.setEmotioncategoryid(Integer.parseInt(secondcursor.getString(secondcursor.getColumnIndex(
                        emotion.COLUMN_EMOTIONCATEGORY_ID
                ))));
            }//end while
            emotionobjList.add(obj);
        }//end while

        db.close();

        return emotionobjList;
    }

    public List<thoughtobj> getThoughtListByLogId(int logid){
        ArrayList<thoughtobj> thoughtobjList=new ArrayList<thoughtobj>();

        db = this.getReadableDatabase();

        String[] projection = {
                "rowid",
                thought.COLUMN_NEGATIVETHOUGHT,
                thought.COLUMN_POSITIVETHOUGHT,
                thought.COLUMN_POSITIVEBELIEFBEFORE,
                thought.COLUMN_NEGATIVEBELIEFBEFORE,
                thought.COLUMN_NEGATIVEBELIEFAFTER,
                thought.COLUMN_LOGENTRY_ID,
        };
        String [] whereargs={Integer.toString(logid)};

        Cursor cursor = db.query(
                thought.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                thought.COLUMN_LOGENTRY_ID+"=?",                                // The columns for the WHERE clause
                whereargs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null // The sort order
        );
        while (cursor.moveToNext()) {
            thoughtobj obj =new thoughtobj();

            obj.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    "rowid"
            ))));
            obj.setPositivebeliefbefore(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    thought.COLUMN_POSITIVEBELIEFBEFORE
            ))));

            obj.setNegativebeliefBefore(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    thought.COLUMN_NEGATIVEBELIEFBEFORE
            ))));
            obj.setNegativebeliefAfter(Integer.parseInt(cursor.getString(cursor.getColumnIndex
                    (thought.COLUMN_NEGATIVEBELIEFAFTER))));

            obj.setPositivethought((cursor.getString(cursor.getColumnIndex(
                    thought.COLUMN_POSITIVETHOUGHT
            ))));

            obj.setNegativethought((cursor.getString(cursor.getColumnIndex(
                    thought.COLUMN_NEGATIVETHOUGHT
            ))));
            obj.setLogentryid(Integer.parseInt(cursor.getString(cursor.getColumnIndex
                    (thought.COLUMN_LOGENTRY_ID))));

            thoughtobjList.add(obj);
        }//end while

        db.close();

        return thoughtobjList;
    }

    public List<thought_cognitivedistortionobj> getThought_cognitivedistortionListByThoughtId(int thoughtid){
        ArrayList<thought_cognitivedistortionobj> thought_cognitivedistortionobjList=new ArrayList<thought_cognitivedistortionobj>();

        db = this.getReadableDatabase();

        String[] projection = {
                "rowid",
                thought_cognitivedistortion.COLUMN_COGNITIVEDISTORTION_ID,
                thought_cognitivedistortion.COLUMN_THOUGHT_ID
        };
        String [] whereargs={Integer.toString(thoughtid)};

        Cursor cursor = db.query(
                thought_cognitivedistortion.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                thought_cognitivedistortion.COLUMN_THOUGHT_ID+"=?",                                // The columns for the WHERE clause
                whereargs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null // The sort order
        );
        while (cursor.moveToNext()) {
            thought_cognitivedistortionobj obj =new thought_cognitivedistortionobj();

            obj.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    "rowid"
            ))));
            obj.setCognitivedistortionid(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    thought_cognitivedistortion.COLUMN_COGNITIVEDISTORTION_ID
            ))));

            obj.setThoughtid(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    thought_cognitivedistortion.COLUMN_THOUGHT_ID
            ))));

            thought_cognitivedistortionobjList.add(obj);
        }//end while

        db.close();

        return thought_cognitivedistortionobjList;
    }







    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd h:mm a", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
