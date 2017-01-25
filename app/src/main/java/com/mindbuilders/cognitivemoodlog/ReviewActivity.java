package com.mindbuilders.cognitivemoodlog;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private List<emotionobj> emotionobjList;
    private List<thoughtobj> thoughtobjList;
    private List<thought_cognitivedistortionobj> thought_cognitivedistortionobjsList;
    private TextView results;
    private ViewGroup vg;

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
        vg=(ViewGroup)findViewById(R.id.reviewresults);




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





        /*myIntent.putExtra("emotionobjList",(ArrayList)getEmotionobjList());
        myIntent.putExtra("thoughtobjList",(ArrayList)getThoughtobjList());
        myIntent.putExtra("thought_cognitivedistortionobj",(ArrayList)getThought_cognitivedistortionobjs());*/
    }
}
