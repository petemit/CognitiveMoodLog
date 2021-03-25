//package com.mindbuilders.cognitivemoodlog;
//
//import android.graphics.Typeface;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutCompat;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.SeekBar;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import com.mindbuilders.cognitivemoodlog.CmlDos.CognitiveDistortionobj;
//import com.mindbuilders.cognitivemoodlog.CmlDos.emotionobj;
//import com.mindbuilders.cognitivemoodlog.CmlDos.logentryobj;
//import com.mindbuilders.cognitivemoodlog.CmlDos.thought_cognitivedistortionobj;
//import com.mindbuilders.cognitivemoodlog.CmlDos.thoughtobj;
//import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;
//import com.mindbuilders.cognitivemoodlog.util.utilities;
//
//import java.util.List;
//
//public class OpenPreviousLogsActivity extends AppCompatActivity {
//    private List<emotionobj> emotionobjList;
//    private List<thoughtobj> thoughtobjList;
//    private List<thought_cognitivedistortionobj> thought_cognitivedistortionobjsList;
//    private List<logentryobj> logobjs;
//    private CogMoodLogDatabaseHelper dbHelper;
//    private List<CognitiveDistortionobj> cogobjs;
//    private ViewGroup vg;
//    private utilities util;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_open_previous_logs);
//        vg=(ViewGroup)findViewById(R.id.previouslogList);
//        dbHelper=new CogMoodLogDatabaseHelper(OpenPreviousLogsActivity.this);
//        cogobjs=dbHelper.getCognitiveDistortionNameList();
//        util=new utilities(OpenPreviousLogsActivity.this);
//        getSupportActionBar().setTitle("Cognitive Mood Log");
////todo fix sort so spinner goes by date.
////todo need to add headings to the two sections
//        logobjs=dbHelper.getLogEntryList();
//        Spinner spin = (Spinner)findViewById(R.id.select_previous_entry_spinner);
//
//        ArrayAdapter<logentryobj> adapter = new ArrayAdapter<logentryobj>(
//                OpenPreviousLogsActivity.this, android.R.layout.simple_spinner_item, logobjs);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spin.setAdapter(adapter);
//
//
//
//
//        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                logentryobj logobj = (logentryobj) ((Spinner) parent).getItemAtPosition(position);
//                if (logobj.getLogid()!=-1){
//                    vg.removeAllViews();
//
//                    emotionobjList=dbHelper.getEmotionObjListByLogid(logobj.getLogid());
//                   /*
//                   */
//                    //vg.addView();
//
//                    //todo this is duplicate code... need to make a method
//                    TextView emo_tv=new TextView(OpenPreviousLogsActivity.this);
//
//
//                    emo_tv.setText("Emotion Review:");
//                    emo_tv.setTypeface(Typeface.DEFAULT_BOLD);
//                    emo_tv.setPadding(15,20,0,20);
//                    emo_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.lastTextSize));
//                    emo_tv.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
//                    vg.addView(emo_tv);
//
//                    for (emotionobj emo : emotionobjList){
//                        LayoutInflater inflater = LayoutInflater.from(vg.getContext());
//                        View tv = inflater.inflate(R.layout.emorow_listitem, vg, false);
//                        TextView emotv=(TextView)tv.findViewById(R.id.emorow_tv);
//                        emotv.setText(emo.getName());
//
//                        SeekBar sb_before=(SeekBar)tv.findViewById(R.id.emorow_sb_before);
//                        SeekBar sb_after=(SeekBar)tv.findViewById(R.id.emorow_sb_after);
//
//                        TextView sb_before_tv=(TextView)tv.findViewById(R.id.emorow_sb_before_tv);
//                        TextView sb_after_tv=(TextView)tv.findViewById(R.id.emorow_sb_after_tv);
//                        sb_before.setProgress(emo.getFeelingStrengthBefore()*10);
//                        sb_after.setProgress(emo.getGetFeelingstrengthAfter()*10);
//                        sb_before.refreshDrawableState();
//                        sb_after.refreshDrawableState();
//                        sb_before.setEnabled(false);
//                        sb_after.setEnabled(false);
//
//                        String s1=    (sb_before_tv.getText() +
//                                Integer.toString(emo.getFeelingStrengthBefore()));
//                        String s2=    (sb_after_tv.getText() +
//                                Integer.toString(emo.getGetFeelingstrengthAfter()));
//                        sb_before_tv.setText(s1);
//                        sb_after_tv.setText(s2);
//
//                        vg.addView(tv);
//
//                    }//end for
//
//                    LayoutInflater dividerinflater= LayoutInflater.from(vg.getContext());
//                    View dividerline=dividerinflater.inflate(R.layout.dividerline,vg,false);
//                    vg.addView(dividerline);
//                    TextView thought_tv=new TextView(OpenPreviousLogsActivity.this);
//                    thought_tv.setText("Negative and Positive Thought Review:");
//                    thought_tv.setTypeface(Typeface.DEFAULT_BOLD);
//                    thought_tv.setPadding(15,20,0,20);
//                    thought_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.lastTextSize));
//                    thought_tv.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
//                    vg.addView(thought_tv);
//
//                    thoughtobjList=dbHelper.getThoughtListByLogId(logobj.getLogid());
//
//                    for (thoughtobj tob : thoughtobjList){
//                        tob.setThought_cognitivedistortionobjList(dbHelper.
//                                getThought_cognitivedistortionListByThoughtId(tob.getId()));
//                    }
//
//                    util.createThoughtReviewView(vg,thoughtobjList, cogobjs);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//    }
//}
