package com.mindbuilders.cognitivemoodlog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.mindbuilders.cognitivemoodlog.CogMoodLogDatabaseContract.*;

/**
 * Created by Peter on 12/30/2016.
 */

public class CogMoodLogDatabaseHelper extends SQLiteOpenHelper{
    public final static String DATABASE_NAME="CognitiveMoodLog.db";
    public final static int DATABASE_VERSION=1;


    public CogMoodLogDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
