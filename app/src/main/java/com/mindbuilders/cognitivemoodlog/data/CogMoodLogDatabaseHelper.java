package com.mindbuilders.cognitivemoodlog.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.util.Log;

import com.mindbuilders.cognitivemoodlog.BaseApplication;
import com.mindbuilders.cognitivemoodlog.CmlDos.CognitiveDistortionobj;
import com.mindbuilders.cognitivemoodlog.CmlDos.emotionobj;
import com.mindbuilders.cognitivemoodlog.CmlDos.logentryobj;
import com.mindbuilders.cognitivemoodlog.CmlDos.thought_cognitivedistortionobj;
import com.mindbuilders.cognitivemoodlog.CmlDos.thoughtobj;
import com.mindbuilders.cognitivemoodlog.CmlDos.troubleshootingobj;
import com.mindbuilders.cognitivemoodlog.MainActivity;

import java.io.File;
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
    public final static String DATABASE_NAME_CN="CognitiveMoodLog";
    public final static int DATABASE_VERSION=1;

    private Cursor cursor;
    SQLiteDatabase db;
    CogMoodLogDatabaseHelper dbHelper;

    public CogMoodLogDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase.loadLibs(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
           db.rawQuery("select * from logentry", null);
        }
        catch (Exception e) {
            Log.i("dbhelper", "creating new db");
            createDb(db);
        }

    }

    public static void passwordProtectDb(boolean result, Context context, String key){


        SQLiteDatabase db = BaseApplication.getDbHelper().getWritableDatabase("");
        File encryptedDbFile = new File((context.getDatabasePath("a").getParentFile()),"encrypted.db");
        File unencryptedFile = new File(db.getPath());


        if (result) {
            BaseApplication.passwordHash = key;
            String sql = String.format("ATTACH DATABASE '%s' AS encrypted KEY '%s'",encryptedDbFile, BaseApplication.passwordHash);
            db.rawExecSQL(sql);
            db.rawExecSQL("SELECT sqlcipher_export('encrypted')");
            db.rawExecSQL("DETACH DATABASE encrypted");
            db.close();
            unencryptedFile.delete();
            encryptedDbFile.renameTo(unencryptedFile);

        }
        else {
            db.rawExecSQL("PRAGMA key = \"" +BaseApplication.passwordHash +"\";");
            String pass = "password";
            db.rawExecSQL("PRAGMA rekey = \"\";");
        }
        db.close();

    }

    public void createDb(SQLiteDatabase db)
    {
        final String SQL_CREATE_COGNITIVEDISTORTION_TABLE =
                "CREATE TABLE " + CogMoodLogDatabaseContract.cognitivedistortion.TABLE_NAME +" (" +
                        // cognitivedistortion._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CogMoodLogDatabaseContract.cognitivedistortion.COLUMN_COGID + " INTEGER, " +
                        CogMoodLogDatabaseContract.cognitivedistortion.COLUMN_NAME + " TEXT, " +
                        CogMoodLogDatabaseContract.cognitivedistortion.COLUMN_DESCRIPTION + " TEXT " +

                        ");";
        db.execSQL(SQL_CREATE_COGNITIVEDISTORTION_TABLE);

        final String SQL_CREATE_EMOTION_TABLE =
                "CREATE TABLE " + CogMoodLogDatabaseContract.emotion.TABLE_NAME +" (" +
                        //   emotion._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CogMoodLogDatabaseContract.emotion.COLUMN_EMOID + " INTEGER, " +
                        CogMoodLogDatabaseContract.emotion.COLUMN_EMOTIONCATEGORY_ID + " INTEGER, " +
                        CogMoodLogDatabaseContract.emotion.COLUMN_NAME + " TEXT " +
                        ");";
        db.execSQL(SQL_CREATE_EMOTION_TABLE);

        final String SQL_CREATE_EMOTION_LOGENTRY_BELIEF_TABLE =
                "CREATE TABLE " + CogMoodLogDatabaseContract.emotion_logentry_belief.TABLE_NAME +" (" +
                        //        emotion_logentry_belief._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_EMOTION_ID + " INTEGER, " +
                        CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_LOGENTRY_ID + " INTEGER, " +
                        CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_BELIEFBEFORE + " INTEGER, " +
                        CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_BELIEFAFTER + " INTEGER " +
                        ");";
        db.execSQL(SQL_CREATE_EMOTION_LOGENTRY_BELIEF_TABLE);

        final String SQL_CREATE_EMOTIONCATEGORY_TABLE =
                "CREATE TABLE " + CogMoodLogDatabaseContract.emotioncategory.TABLE_NAME +" (" +
                        //      emotioncategory._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CogMoodLogDatabaseContract.emotioncategory.COLUMN_EMOCATID + " INTEGER, " +
                        CogMoodLogDatabaseContract.emotioncategory.COLUMN_NAME + " TEXT " +
                        ");";
        db.execSQL(SQL_CREATE_EMOTIONCATEGORY_TABLE);

        final String SQL_CREATE_LOGENTRY_TABLE =
                "CREATE TABLE " + CogMoodLogDatabaseContract.logentry.TABLE_NAME +" (" +
                        //    logentry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CogMoodLogDatabaseContract.logentry.COLUMN_LOGENTRY + " TEXT, " +
                        CogMoodLogDatabaseContract.logentry.COLUMN_USER_ID+ " INTEGER, " +
                        CogMoodLogDatabaseContract.logentry.COLUMN_ISFINISHED + " BOOLEAN, " +
                        CogMoodLogDatabaseContract.logentry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                        ");";
        db.execSQL(SQL_CREATE_LOGENTRY_TABLE);

        final String SQL_CREATE_THOUGHT_TABLE =
                "CREATE TABLE " + CogMoodLogDatabaseContract.thought.TABLE_NAME +" (" +
                        //  thought._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CogMoodLogDatabaseContract.thought.COLUMN_NEGATIVETHOUGHT + " TEXT, " +
                        CogMoodLogDatabaseContract.thought.COLUMN_POSITIVETHOUGHT + " TEXT, " +
                        CogMoodLogDatabaseContract.thought.COLUMN_LOGENTRY_ID + " INTEGER, " +
                        CogMoodLogDatabaseContract.thought.COLUMN_NEGATIVEBELIEFBEFORE + " INTEGER, " +
                        CogMoodLogDatabaseContract.thought.COLUMN_NEGATIVEBELIEFAFTER + " INTEGER, " +
                        CogMoodLogDatabaseContract.thought.COLUMN_POSITIVEBELIEFBEFORE + " INTEGER, " +
                        CogMoodLogDatabaseContract.thought.COLUMN_POSITIVEBELIEFAFTER + " INTEGER " +
                        ");";
        db.execSQL(SQL_CREATE_THOUGHT_TABLE);

        final String SQL_CREATE_THOUGHT_COGNITIVEDISTORTION_TABLE =
                "CREATE TABLE " + CogMoodLogDatabaseContract.thought_cognitivedistortion.TABLE_NAME +" (" +
                        //  thought_cognitivedistortion._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CogMoodLogDatabaseContract.thought_cognitivedistortion.COLUMN_THOUGHT_ID + " INTEGER, " +
                        CogMoodLogDatabaseContract.thought_cognitivedistortion.COLUMN_COGNITIVEDISTORTION_ID + " INTEGER " +
                        ");";
        db.execSQL(SQL_CREATE_THOUGHT_COGNITIVEDISTORTION_TABLE);

        final String SQL_CREATE_TROUBLESHOOTINGGUIDELINES_TABLE =
                "CREATE TABLE " + CogMoodLogDatabaseContract.troubleshootingguidelines.TABLE_NAME +" (" +
                        //  troubleshootingguidelines._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CogMoodLogDatabaseContract.troubleshootingguidelines.COLUMN_TROUBLESHOOTID + " INTEGER, " +
                        CogMoodLogDatabaseContract.troubleshootingguidelines.COLUMN_QUESTION + " TEXT, " +
                        CogMoodLogDatabaseContract.troubleshootingguidelines.COLUMN_EXPLANATION + " TEXT " +
                        ");";
        db.execSQL(SQL_CREATE_TROUBLESHOOTINGGUIDELINES_TABLE);

    }

    public String saveLogEntry(List<emotionobj> emotionobjList, List<thought_cognitivedistortionobj> thought_cognitivedistortionobjList , List<thoughtobj> thoughtobjList, String situation) {

        db = this.getWritableDatabase(BaseApplication.passwordHash);
        //Input the situation
        ContentValues cv = new ContentValues();
        cv.put(CogMoodLogDatabaseContract.logentry.COLUMN_LOGENTRY,situation.trim());
        cv.put(CogMoodLogDatabaseContract.logentry.COLUMN_TIMESTAMP,getDateTime());
        long logid=db.insert(CogMoodLogDatabaseContract.logentry.TABLE_NAME,null,cv);

        //input the thoughts associated
        for (thoughtobj tob : thoughtobjList){
            ContentValues tobcv = new ContentValues();
            tobcv.put(CogMoodLogDatabaseContract.thought.COLUMN_LOGENTRY_ID,logid);
            tobcv.put(CogMoodLogDatabaseContract.thought.COLUMN_NEGATIVEBELIEFAFTER,tob.getNegativebeliefAfter());
            tobcv.put(CogMoodLogDatabaseContract.thought.COLUMN_NEGATIVEBELIEFBEFORE,tob.getNegativebeliefBefore());
            tobcv.put(CogMoodLogDatabaseContract.thought.COLUMN_NEGATIVETHOUGHT,tob.getNegativethought());
            tobcv.put(CogMoodLogDatabaseContract.thought.COLUMN_POSITIVEBELIEFBEFORE,tob.getPositivebeliefbefore());
            tobcv.put(CogMoodLogDatabaseContract.thought.COLUMN_POSITIVETHOUGHT,tob.getPositivethought());
            //add the thought to the db
            long tobDbId= db.insert(CogMoodLogDatabaseContract.thought.TABLE_NAME,null,tobcv);

            //add the cognitive distortion linked to the thought

            for(thought_cognitivedistortionobj tcdo:thought_cognitivedistortionobjList){
                if (tcdo.getThoughtid()==tob.getId())
                {
                    ContentValues tcdo_cv = new ContentValues();
                    tcdo_cv.put(CogMoodLogDatabaseContract.thought_cognitivedistortion.COLUMN_COGNITIVEDISTORTION_ID
                            ,tcdo.getCognitivedistortionid());
                    tcdo_cv.put(CogMoodLogDatabaseContract.thought_cognitivedistortion.COLUMN_THOUGHT_ID,tobDbId);
                    db.insert(CogMoodLogDatabaseContract.thought_cognitivedistortion.TABLE_NAME,null,tcdo_cv);
                }
            }

        }//end tob for
        for (emotionobj emo : emotionobjList){
            ContentValues emocv=new ContentValues();
            emocv.put(CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_LOGENTRY_ID,logid);
            emocv.put(CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_BELIEFAFTER,emo.getFeelingStrengthBefore());
            emocv.put(CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_BELIEFBEFORE,emo.getGetFeelingstrengthAfter());
            emocv.put(CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_EMOTION_ID,emo.getId());
            db.insert(CogMoodLogDatabaseContract.emotion_logentry_belief.TABLE_NAME,null,emocv);
        }
        return null;
    }

    public List<CognitiveDistortionobj> getCognitiveDistortionNameList(){
        List<CognitiveDistortionobj> cogobjs=new ArrayList<CognitiveDistortionobj>();
        /* Use CogMoodLogDatabaseHelper to get access to a readable database */
        db = this.getReadableDatabase(BaseApplication.passwordHash);
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
        db = this.getReadableDatabase(BaseApplication.passwordHash);
        //   db=dbHelper.getReadableDatabase();
        String[] projection = {
                "rowid",
                CogMoodLogDatabaseContract.logentry.COLUMN_TIMESTAMP,
                CogMoodLogDatabaseContract.logentry.COLUMN_LOGENTRY
        };

        String sortOrder =
                CogMoodLogDatabaseContract.logentry.COLUMN_TIMESTAMP + " DESC";

        Cursor cursor = db.query(
                CogMoodLogDatabaseContract.logentry.TABLE_NAME,                     // The table to query
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

        db=this.getReadableDatabase(BaseApplication.passwordHash);

        String[] projection = {
                "rowid",
                CogMoodLogDatabaseContract.troubleshootingguidelines.COLUMN_EXPLANATION,
                CogMoodLogDatabaseContract.troubleshootingguidelines.COLUMN_QUESTION
        };
        String sortOrder =
                CogMoodLogDatabaseContract.troubleshootingguidelines.COLUMN_QUESTION + " DESC";
        Cursor cursor = db.query(
                CogMoodLogDatabaseContract.troubleshootingguidelines.TABLE_NAME,                     // The table to query
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
                    CogMoodLogDatabaseContract.troubleshootingguidelines.COLUMN_EXPLANATION
            )));

            obj.setQuestion(cursor.getString(cursor.getColumnIndex(
                    CogMoodLogDatabaseContract.troubleshootingguidelines.COLUMN_QUESTION
            )));
        troubleshootingList.add(obj);
        }
        return troubleshootingList;
    }

    public List<emotionobj> getEmotionObjListByLogid(int logid){
        ArrayList<emotionobj> emotionobjList=new ArrayList<emotionobj>();

        db = this.getReadableDatabase(BaseApplication.passwordHash);


        String[] projection = {
                "rowid",
                CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_BELIEFBEFORE,
                CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_BELIEFAFTER,
                CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_EMOTION_ID,
                CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_LOGENTRY_ID,
        };
        String [] whereargs={Integer.toString(logid)};

        Cursor cursor = db.query(
                CogMoodLogDatabaseContract.emotion_logentry_belief.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_LOGENTRY_ID+"=?",                                // The columns for the WHERE clause
                whereargs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null // The sort order
        );
        while (cursor.moveToNext()) {
            emotionobj obj =new emotionobj();

            obj.setGetFeelingstrengthAfter(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_BELIEFAFTER
            ))));
            obj.setFeelingstrengthBefore(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_BELIEFBEFORE
            ))));

            obj.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_EMOTION_ID
            ))));
            obj.setLogentryid(Integer.parseInt(cursor.getString(cursor.getColumnIndex
                    (CogMoodLogDatabaseContract.emotion_logentry_belief.COLUMN_LOGENTRY_ID))));

            //Now get the emotion names


            String[] secondprojection = {
                    "rowid",
                    CogMoodLogDatabaseContract.emotion.COLUMN_NAME,
                    CogMoodLogDatabaseContract.emotion.COLUMN_EMOTIONCATEGORY_ID,
            };

            String sortOrder =
                    "rowid"+ " DESC";
            String [] secondwhereargs={Integer.toString(obj.getId())};
            Cursor secondcursor = db.query(
                    CogMoodLogDatabaseContract.emotion.TABLE_NAME,                     // The table to query
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
                        CogMoodLogDatabaseContract.emotion.COLUMN_NAME
                )));
                obj.setEmotioncategoryid(Integer.parseInt(secondcursor.getString(secondcursor.getColumnIndex(
                        CogMoodLogDatabaseContract.emotion.COLUMN_EMOTIONCATEGORY_ID
                ))));
            }//end while
            emotionobjList.add(obj);
        }//end while

        db.close();

        return emotionobjList;
    }

    public List<thoughtobj> getThoughtListByLogId(int logid){
        ArrayList<thoughtobj> thoughtobjList=new ArrayList<thoughtobj>();

        db = this.getReadableDatabase(BaseApplication.passwordHash);

        String[] projection = {
                "rowid",
                CogMoodLogDatabaseContract.thought.COLUMN_NEGATIVETHOUGHT,
                CogMoodLogDatabaseContract.thought.COLUMN_POSITIVETHOUGHT,
                CogMoodLogDatabaseContract.thought.COLUMN_POSITIVEBELIEFBEFORE,
                CogMoodLogDatabaseContract.thought.COLUMN_NEGATIVEBELIEFBEFORE,
                CogMoodLogDatabaseContract.thought.COLUMN_NEGATIVEBELIEFAFTER,
                CogMoodLogDatabaseContract.thought.COLUMN_LOGENTRY_ID,
        };
        String [] whereargs={Integer.toString(logid)};

        Cursor cursor = db.query(
                CogMoodLogDatabaseContract.thought.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                CogMoodLogDatabaseContract.thought.COLUMN_LOGENTRY_ID+"=?",                                // The columns for the WHERE clause
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
                    CogMoodLogDatabaseContract.thought.COLUMN_POSITIVEBELIEFBEFORE
            ))));

            obj.setNegativebeliefBefore(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    CogMoodLogDatabaseContract.thought.COLUMN_NEGATIVEBELIEFBEFORE
            ))));
            obj.setNegativebeliefAfter(Integer.parseInt(cursor.getString(cursor.getColumnIndex
                    (CogMoodLogDatabaseContract.thought.COLUMN_NEGATIVEBELIEFAFTER))));

            obj.setPositivethought((cursor.getString(cursor.getColumnIndex(
                    CogMoodLogDatabaseContract.thought.COLUMN_POSITIVETHOUGHT
            ))));

            obj.setNegativethought((cursor.getString(cursor.getColumnIndex(
                    CogMoodLogDatabaseContract.thought.COLUMN_NEGATIVETHOUGHT
            ))));
            obj.setLogentryid(Integer.parseInt(cursor.getString(cursor.getColumnIndex
                    (CogMoodLogDatabaseContract.thought.COLUMN_LOGENTRY_ID))));

            thoughtobjList.add(obj);
        }//end while

        db.close();

        return thoughtobjList;
    }

    public List<thought_cognitivedistortionobj> getThought_cognitivedistortionListByThoughtId(int thoughtid){
        ArrayList<thought_cognitivedistortionobj> thought_cognitivedistortionobjList=new ArrayList<thought_cognitivedistortionobj>();

        db = this.getReadableDatabase(BaseApplication.passwordHash);

        String[] projection = {
                "rowid",
                CogMoodLogDatabaseContract.thought_cognitivedistortion.COLUMN_COGNITIVEDISTORTION_ID,
                CogMoodLogDatabaseContract.thought_cognitivedistortion.COLUMN_THOUGHT_ID
        };
        String [] whereargs={Integer.toString(thoughtid)};

        Cursor cursor = db.query(
                CogMoodLogDatabaseContract.thought_cognitivedistortion.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                CogMoodLogDatabaseContract.thought_cognitivedistortion.COLUMN_THOUGHT_ID+"=?",                                // The columns for the WHERE clause
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
                    CogMoodLogDatabaseContract.thought_cognitivedistortion.COLUMN_COGNITIVEDISTORTION_ID
            ))));

            obj.setThoughtid(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    CogMoodLogDatabaseContract.thought_cognitivedistortion.COLUMN_THOUGHT_ID
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
