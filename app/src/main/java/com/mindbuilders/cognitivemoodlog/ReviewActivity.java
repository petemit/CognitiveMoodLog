package com.mindbuilders.cognitivemoodlog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private List<emotionobj> emotionobjList;
    private List<thoughtobj> thoughtobjList;
    private List<thought_cognitivedistortionobj> thought_cognitivedistortionobjsList;
    private CogMoodLogDatabaseHelper dbHelper;
    private List<CognitiveDistortionobj> cogobjs;
    private ViewGroup vg;
    private Button editbutton;
    private Button deletebutton;
    private Button savebutton;
    private String situation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent

        emotionobjList = (ArrayList<emotionobj>) myIntent.getSerializableExtra("emotionobjList"); // will return "FirstKeyValue"
        thoughtobjList = (ArrayList<thoughtobj>) myIntent.getSerializableExtra("thoughtobjList");
        thought_cognitivedistortionobjsList= (ArrayList<thought_cognitivedistortionobj>)myIntent.
                getSerializableExtra("thought_cognitivedistortionobj");//
        situation=(String)myIntent.getStringExtra("situation");
        dbHelper=new CogMoodLogDatabaseHelper(getBaseContext());
        cogobjs=dbHelper.getCognitiveDistortionNameList();
        editbutton=(Button)findViewById(R.id.review_edit_button);
        deletebutton=(Button)findViewById(R.id.review_delete_button);
        savebutton=(Button)findViewById(R.id.review_save_button);

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);

// 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("If you delete this entry, all your entered data will be lost!")
                        .setTitle("Are you sure?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
                        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

// 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dbHelper.saveLogEntry(emotionobjList,thought_cognitivedistortionobjsList,thoughtobjList, situation);
               Toast.makeText(ReviewActivity.this,"Logentry Saved",Toast.LENGTH_LONG).show();
               Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
               startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        vg=(ViewGroup)findViewById(R.id.reviewresults);



        TextView emo_tv=new TextView(this.getBaseContext());
















        emo_tv.setText("Emotion Review:");
        emo_tv.setTypeface(Typeface.DEFAULT_BOLD);
        emo_tv.setPadding(15,20,0,20);
        emo_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.lastTextSize));
        emo_tv.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
        vg.addView(emo_tv);

        for (emotionobj emo : emotionobjList){
            LayoutInflater inflater = LayoutInflater.from(vg.getContext());
            View tv = inflater.inflate(R.layout.emorow_listitem, vg, false);
            TextView emotv=(TextView)tv.findViewById(R.id.emorow_tv);
            emotv.setText(emo.getName());

            SeekBar sb_before=(SeekBar)tv.findViewById(R.id.emorow_sb_before);
            SeekBar sb_after=(SeekBar)tv.findViewById(R.id.emorow_sb_after);

            TextView sb_before_tv=(TextView)tv.findViewById(R.id.emorow_sb_before_tv);
            TextView sb_after_tv=(TextView)tv.findViewById(R.id.emorow_sb_after_tv);
            sb_before.setProgress(emo.getFeelingStrengthBefore()*10);
            sb_after.setProgress(emo.getGetFeelingstrengthAfter()*10);
            sb_before.refreshDrawableState();
            sb_after.refreshDrawableState();
            sb_before.setEnabled(false);
            sb_after.setEnabled(false);

        String s1=    (sb_before_tv.getText() +
                    Integer.toString(emo.getFeelingStrengthBefore()));
        String s2=    (sb_after_tv.getText() +
                    Integer.toString(emo.getGetFeelingstrengthAfter()));
            sb_before_tv.setText(s1);
            sb_after_tv.setText(s2);

            vg.addView(tv);

        }
        LayoutInflater dividerinflater= LayoutInflater.from(vg.getContext());
        View dividerline=dividerinflater.inflate(R.layout.dividerline,vg,false);
        vg.addView(dividerline);
        TextView thought_tv=new TextView(this.getBaseContext());
        thought_tv.setText("Negative and Positive Thought Review:");
        thought_tv.setTypeface(Typeface.DEFAULT_BOLD);
        thought_tv.setPadding(15,20,0,20);
        thought_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.lastTextSize));
        thought_tv.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
        vg.addView(thought_tv);
        for (thoughtobj tob : thoughtobjList){


            LayoutInflater inflater = LayoutInflater.from(vg.getContext());
            View tv = inflater.inflate(R.layout.thoughtrow_listitem, vg, false);
            TextView negthoughttv=(TextView)tv.findViewById(R.id.thoughtrow_negativethought_tv);
            TextView posthoughttv=(TextView)tv.findViewById(R.id.thoughtrow_positivethought_tv);
            negthoughttv.setText(tob.getNegativethought());
            posthoughttv.setText(tob.getPositivethought());
            TextView cogdist_tv=(TextView)tv.findViewById(R.id.review_cognitivedistortion_tv);
            for (thought_cognitivedistortionobj tcdo : thought_cognitivedistortionobjsList){
                if (tcdo.getThoughtid()==tob.getId()){
                    int cid =tcdo.getCognitivedistortionid();
                    for (CognitiveDistortionobj cog : cogobjs){
                        if (cog.getId() == cid && cog.getId()>0){
                            cogdist_tv.setText(cog.getName());
                        }
                    }
                }
            }
            //todo something tells me this nasty nested thing is bad

            SeekBar neg_sb_before=(SeekBar)tv.findViewById(R.id.thoughtrow_neg_sb_before);
            SeekBar neg_sb_after=(SeekBar)tv.findViewById(R.id.thoughtrow_neg_sb_after);
            SeekBar pos_sb_before=(SeekBar)tv.findViewById(R.id.thoughtrow_pos_sb_before);


            TextView neg_sb_before_tv=(TextView)tv.findViewById(R.id.thoughtrow_neg_sb_before_tv);
            TextView neg_sb_after_tv=(TextView)tv.findViewById(R.id.thoughtrow_neg_sb_after_tv);
            TextView pos_sb_before_tv=(TextView)tv.findViewById(R.id.thoughtrow_pos_sb_before_tv);


            neg_sb_before.setProgress(tob.getNegativebeliefBefore()*10);
            neg_sb_after.setProgress(tob.getNegativebeliefAfter()*10);
            pos_sb_before.setProgress(tob.getPositivebeliefbefore()*10);

            neg_sb_before.setEnabled(false);
            neg_sb_after.setEnabled(false);
            pos_sb_before.setEnabled(false);


            String s1=    (neg_sb_before_tv.getText() +
                    Integer.toString(tob.getNegativebeliefBefore()));
            String s2=    (neg_sb_after_tv.getText() +
                    Integer.toString(tob.getNegativebeliefAfter()));
            String s3=    (pos_sb_before_tv.getText() +
                    Integer.toString(tob.getPositivebeliefbefore()));

            neg_sb_before_tv.setText(s1);
            neg_sb_after_tv.setText(s2);
            pos_sb_before_tv.setText(s3);

            vg.addView(tv);

        }





        /*myIntent.putExtra("emotionobjList",(ArrayList)getEmotionobjList());
        myIntent.putExtra("thoughtobjList",(ArrayList)getThoughtobjList());
        myIntent.putExtra("thought_cognitivedistortionobj",(ArrayList)getThought_cognitivedistortionobjs());*/
    }
}
