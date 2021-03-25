//package com.mindbuilders.cognitivemoodlog.util;
//
//import android.content.Context;
//import android.database.Cursor;
//import net.sqlcipher.database.SQLiteDatabase;
//import android.util.Log;
//
//import com.opencsv.CSVWriter;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//
///**
// * Can export an sqlite databse into a csv file.
// *
// * The file has on the top dbVersion and on top of each table data the name of the table
// *
// * Inspired by
// * https://stackoverflow.com/questions/31367270/exporting-sqlite-database-to-csv-file-in-android
// * and some other SO threads as well.
// *
// */
//public class SqliteExporter {
//    private static final String TAG = SqliteExporter.class.getSimpleName();
//
//    public static final String DB_BACKUP_DB_VERSION_KEY = "dbVersion";
//    public static final String DB_BACKUP_TABLE_NAME = "table";
//
//    public static String export(SQLiteDatabase db, Context context) throws IOException{
//        if( !utilities.isExternalStorageWritable() ){
//            throw new IOException("Cannot write to external storage");
//        }
//        File backupFile = new File(utilities.getDownloadsDir(context));
//        boolean success = backupFile.createNewFile();
//        if(!success){
//            throw new IOException("Failed to create the backup file");
//        }
//     //   List<String> tables = getTablesOnDataBase(db);
//        Log.d(TAG, "Started to fill the backup file in " + backupFile.getAbsolutePath());
//        long starTime = System.currentTimeMillis();
//        writeCsv(backupFile, db);
//        long endTime = System.currentTimeMillis();
//        Log.d(TAG, "Creating backup took " + (endTime - starTime) + "ms.");
//
//        return backupFile.getAbsolutePath();
//    }
//
//
//
//    /**
//     * Get all the table names we have in db
//     *
//     * @param db
//     * @return
//     */
////    public static List<String> getTablesOnDataBase(SQLiteDatabase db){
////        Cursor c = null;
////        List<String> tables = new ArrayList<>();
////        try{
////            c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
////            if (c.moveToFirst()) {
////                while ( !c.isAfterLast() ) {
////                    tables.add(c.getString(0));
////                    c.moveToNext();
////                }
////            }
////        }
////        catch(Exception throwable){
////            Log.e(TAG, "Could not get the table names from db", throwable);
////        }
////        finally{
////            if(c!=null) {
////                c.close();
////            }
////            if (db != null) {
////                db.close();
////            }
////        }
////        return tables;
////    }
//
//    private static void writeCsv(File backupFile, SQLiteDatabase db){
//        CSVWriter csvWrite = null;
//        Cursor curCSV = null;
//        try {
//            csvWrite = new CSVWriter(new FileWriter(backupFile));
//            writeSingleValue(csvWrite, DB_BACKUP_DB_VERSION_KEY + "=" + db.getVersion());
//          //  for(String table: tables){
//                writeSingleValue(csvWrite, DB_BACKUP_TABLE_NAME + "=" + "logentries");
//                curCSV = db.rawQuery("select logentry.logentry, logentry.timestamp, logentry.rowid as logentry_id, e.name as emotionName, elb.beliefbefore as emotionBefore ,elb.beliefafter as emotionAfter, e.emotioncategory_id as emotionCategory, t.negativebeliefbefore as NegativeThoughtBeliefBefore, t.negativebeliefafter as NegativeThoughtBeliefAfter, t.positivebeliefbefore as PositiveThoughtBelief, t.positivethought, t.negativethought, cd.name as CognitiveDistortionName, cd.description as CognitiveDistortionDescription from logentry left join emotion_logentry_belief as elb on elb.logentry_id = logentry.rowid left join emotion as e on elb.emotion_id = e.rowid left join thought as t on t.logentry_id = logentry.rowid left join thought_cognitivedistortion tc on t.rowid=tc.thought_id left join cognitivedistortion as cd on cd.rowid=tc.cognitivedistortion_id left join emotion_logentry_belief",null);
//                csvWrite.writeNext(curCSV.getColumnNames());
//                while(curCSV.moveToNext()) {
//                    int columns = curCSV.getColumnCount();
//                    String[] columnArr = new String[columns];
//                    for( int i = 0; i < columns; i++){
//                        columnArr[i] = curCSV.getString(i);
//                    }
//                    csvWrite.writeNext(columnArr);
//                }
//           // }
//        }
//        catch(Exception sqlEx) {
//            Log.e(TAG, sqlEx.getMessage(), sqlEx);
//        }finally {
//            if(csvWrite != null){
//                try {
//                    csvWrite.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if( curCSV != null ){
//                curCSV.close();
//            }
//        }
//    }
//
//    private static void writeSingleValue(CSVWriter writer, String value){
//        writer.writeNext(new String[]{value});
//    }
//}