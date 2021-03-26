//package com.mindbuilders.cognitivemoodlog;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.com.mindbuilders.cognitivemoodlog.view.LayoutInflater;
//import android.com.mindbuilders.cognitivemoodlog.view.View;
//import android.com.mindbuilders.cognitivemoodlog.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.mindbuilders.cognitivemoodlog.CmlDos.troubleshootingobj;
//import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;
//
//import java.util.ArrayList;
//
//public class TroubleshootEntryActivity extends AppCompatActivity {
//    private CogMoodLogDatabaseHelper dbhelper;
//    private ViewGroup troubleshootinglist;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_troubleshoot_entry);
//        getSupportActionBar().setTitle("Cognitive Mood Log");
//        dbhelper=new CogMoodLogDatabaseHelper(TroubleshootEntryActivity.this);
//        troubleshootinglist=(ViewGroup)findViewById(R.id.troubleshootentry_list);
//        Button returnbutton=(Button)findViewById(R.id.returnbutton);
//        returnbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        ArrayList<troubleshootingobj> troubleshootingobjList= (ArrayList)dbhelper.getTroubleshootingObj();
//        for (troubleshootingobj tO:troubleshootingobjList){
//            LayoutInflater inflater= LayoutInflater.from(troubleshootinglist.getContext());
//            View troubleshootingitem=inflater.inflate(R.layout.troubleshooting_listitem,troubleshootinglist,false);
//            TextView question =(TextView)troubleshootingitem.findViewById(R.id.troubleshootingquestion_tv);
//            TextView answer =(TextView)troubleshootingitem.findViewById(R.id.troubleshootinganswer_tv);
//            question.setText(tO.getQuestion());
//            answer.setText(tO.getAnswer());
//            troubleshootinglist.addView(troubleshootingitem);
//        }
//
//    }
//}
