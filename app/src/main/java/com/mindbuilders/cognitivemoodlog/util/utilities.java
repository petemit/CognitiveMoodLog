//package com.mindbuilders.cognitivemoodlog.util;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Environment;
//import android.support.annotation.NonNull;
//import android.util.Log;
//import android.com.mindbuilders.cognitivemoodlog.view.LayoutInflater;
//import android.com.mindbuilders.cognitivemoodlog.view.View;
//import android.com.mindbuilders.cognitivemoodlog.view.ViewGroup;
//import android.com.mindbuilders.cognitivemoodlog.view.inputmethod.InputMethodManager;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//
//import com.google.android.gms.tasks.Continuation;
//import com.google.android.gms.tasks.Task;
//import com.google.android.gms.tasks.Tasks;
//import com.mindbuilders.cognitivemoodlog.BaseApplication;
//import com.mindbuilders.cognitivemoodlog.CmlDos.CognitiveDistortionobj;
//import com.mindbuilders.cognitivemoodlog.CmlDos.thought_cognitivedistortionobj;
//import com.mindbuilders.cognitivemoodlog.CmlDos.thoughtobj;
//import com.mindbuilders.cognitivemoodlog.R;
//
//import java.io.File;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
//import java.io.Writer;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by Peter on 1/19/2017.
// */
//
//public class utilities {
//    private Context context;
//    private static final int REQUEST_CODE_SIGN_IN = 0;
//
//    public utilities(Context context) {
//        this.context = context;
//    }
//
//    public void hideKeyboard(View com.mindbuilders.cognitivemoodlog.view) {
//        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(com.mindbuilders.cognitivemoodlog.view.getWindowToken(), 0);
//    }
//
//    public void createThoughtReviewView(ViewGroup vg, List<thoughtobj> thoughtobjList, List<CognitiveDistortionobj> cogobjs) {
//        for (thoughtobj tob : thoughtobjList) {
//            ArrayList<thought_cognitivedistortionobj> thought_cognitivedistortionobjsList =
//                    (ArrayList) tob.getThought_cognitivedistortionobjList();
//
//            LayoutInflater inflater = LayoutInflater.from(vg.getContext());
//            View tv = inflater.inflate(R.layout.thoughtrow_listitem, vg, false);
//            TextView negthoughttv = (TextView) tv.findViewById(R.id.thoughtrow_negativethought_tv);
//            TextView posthoughttv = (TextView) tv.findViewById(R.id.thoughtrow_positivethought_tv);
//            negthoughttv.setText(tob.getNegativethought());
//            posthoughttv.setText(tob.getPositivethought());
//            TextView cogdist_tv = (TextView) tv.findViewById(R.id.review_cognitivedistortion_tv);
//            for (thought_cognitivedistortionobj tcdo : thought_cognitivedistortionobjsList) {
//                if (tcdo.getThoughtid() == tob.getId()) {
//                    int cid = tcdo.getCognitivedistortionid();
//                    for (CognitiveDistortionobj cog : cogobjs) {
//                        if (cog.getId() == cid && cog.getId() > 0) {
//                            cogdist_tv.setText(cog.getName());
//                        }
//                    }
//                }
//            }
//            //todo something tells me this nasty nested thing is bad
//
//            SeekBar neg_sb_before = (SeekBar) tv.findViewById(R.id.thoughtrow_neg_sb_before);
//            SeekBar neg_sb_after = (SeekBar) tv.findViewById(R.id.thoughtrow_neg_sb_after);
//            SeekBar pos_sb_before = (SeekBar) tv.findViewById(R.id.thoughtrow_pos_sb_before);
//
//
//            TextView neg_sb_before_tv = (TextView) tv.findViewById(R.id.thoughtrow_neg_sb_before_tv);
//            TextView neg_sb_after_tv = (TextView) tv.findViewById(R.id.thoughtrow_neg_sb_after_tv);
//            TextView pos_sb_before_tv = (TextView) tv.findViewById(R.id.thoughtrow_pos_sb_before_tv);
//
//
//            neg_sb_before.setProgress(tob.getNegativebeliefBefore() * 10);
//            neg_sb_after.setProgress(tob.getNegativebeliefAfter() * 10);
//            pos_sb_before.setProgress(tob.getPositivebeliefbefore() * 10);
//
//            neg_sb_before.setEnabled(false);
//            neg_sb_after.setEnabled(false);
//            pos_sb_before.setEnabled(false);
//
//
//            String s1 = (neg_sb_before_tv.getText() +
//                    Integer.toString(tob.getNegativebeliefBefore()));
//            String s2 = (neg_sb_after_tv.getText() +
//                    Integer.toString(tob.getNegativebeliefAfter()));
//            String s3 = (pos_sb_before_tv.getText() +
//                    Integer.toString(tob.getPositivebeliefbefore()));
//
//            neg_sb_before_tv.setText(s1);
//            neg_sb_after_tv.setText(s2);
//            pos_sb_before_tv.setText(s3);
//
//            vg.addView(tv);
//
//        }
//
//    }
//    public static String getSha1Hex(String clearString)
//    {
//        try
//        {
//            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
//            messageDigest.update(clearString.getBytes("UTF-8"));
//            byte[] bytes = messageDigest.digest();
//            StringBuilder buffer = new StringBuilder();
//            for (byte b : bytes)
//            {
//                buffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
//            }
//            return buffer.toString();
//        }
//        catch (Exception ignored)
//        {
//            ignored.printStackTrace();
//            return null;
//        }
//    }
//
////    private void createFileInAppFolder() {
////        final Task<DriveFolder> appFolderTask = Drive.getDriveResourceClient(context,BaseApplication).getAppFolder();
////        final Task<DriveContents> createContentsTask = Drive.getDriveResourceClient().createContents();
////        Tasks.whenAll(appFolderTask, createContentsTask)
////                .continueWithTask(new Continuation<Void, Task<DriveFile>>() {
////                    @Override
////                    public Task<DriveFile> then(@NonNull Task<Void> task) throws Exception {
////                        DriveFolder parent = appFolderTask.getResult();
////                        DriveContents contents = createContentsTask.getResult();
////                        OutputStream outputStream = contents.getOutputStream();
////                        try (Writer writer = new OutputStreamWriter(outputStream)) {
////                            writer.write("Hello World!");
////                        }
////
////                        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
////                                .setTitle("New file")
////                                .setMimeType("text/plain")
////                                .setStarred(true)
////                                .build();
////
////                        return getDriveResourceClient().createFile(parent, changeSet, contents);
////                    }
////                })
////                .addOnSuccessListener(this,
////                        new OnSuccessListener<DriveFile>() {
////                            @Override
////                            public void onSuccess(DriveFile driveFile) {
////                                showMessage(getString(R.string.file_created,
////                                        driveFile.getDriveId().encodeToString()));
////                                finish();
////                            }
////                        })
////                .addOnFailureListener(this, new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception e) {
////                        Log.e(TAG, "Unable to create file", e);
////                        showMessage(getString(R.string.file_create_error));
////                        finish();
////                    }
////                });
////    }
//
//
//
//    public static String getDownloadsDir(Context context){
//        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/cogmoodlog_"+ createBackupFileName();
//    }
//
//    private static String createBackupFileName(){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_SSSS");
//        return sdf.format(new Date()) + ".csv";
//    }
//
//    public static File createDirIfNotExist(String path){
//        File dir = new File(path);
//        if( !dir.exists() ){
//            dir.mkdir();
//        }
//        return dir;
//    }
//
//    /* Checks if external storage is available for read and write */
//    public static boolean isExternalStorageWritable() {
//        String state = Environment.getExternalStorageState();
//        return Environment.MEDIA_MOUNTED.equals(state);
//    }
//
//    /* Checks if external storage is available to at least read */
//    public static boolean isExternalStorageReadable() {
//        String state = Environment.getExternalStorageState();
//        return Environment.MEDIA_MOUNTED.equals(state) ||
//                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
//    }
//}
//
//
//
//
