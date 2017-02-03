package com.mindbuilders.cognitivemoodlog;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.content.Context;

import java.io.IOException;
import java.util.List;


public class EmotionPickerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String Title = "Describe the Situation you would like to Capture";
    TextView situationDescription;

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;
    RecyclerView emotionlist;
    EmotionRVAdapter rvAdapter;
    private CreateNewLogEntry parent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_emotion_picker, container, false);
        parent=(CreateNewLogEntry)getActivity();
        situationDescription = (TextView) rootView.findViewById(R.id.SituationDescription);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        emotionlist=(RecyclerView)rootView.findViewById(R.id.rv_emotions);




        dbHelper = new CogMoodLogDatabaseHelper(getContext());

        /* Use CogMoodLogDatabaseHelper to get access to a readable database */
        db = dbHelper.getReadableDatabase();
        //   db=dbHelper.getReadableDatabase();
        String[] projection = {
                "rowid",
                CogMoodLogDatabaseContract.emotioncategory.COLUMN_NAME,
        };

        String sortOrder =
                CogMoodLogDatabaseContract.emotioncategory.COLUMN_NAME + " DESC";

        Cursor cursor = db.query(
                CogMoodLogDatabaseContract.emotioncategory.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        rvAdapter=new EmotionRVAdapter(cursor.getCount(),cursor, db,getActivity());

        emotionlist.setLayoutManager(layoutManager);
        emotionlist.setAdapter(rvAdapter);

        //todo Figure out a way to use the recyclerview without stopping recycling like I'm doing here
        emotionlist.setItemViewCacheSize(cursor.getCount());




        return rootView;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && parent !=null){
            parent.setLeftNavVisible();
        }
    }

    @Override
    public void onStart() {
        situationDescription.setText(((CreateNewLogEntry)getActivity()).getSituation());
        super.onStart();
    }

    @Override
    public void onResume() {
        situationDescription.setText(((CreateNewLogEntry)getActivity()).getSituation());
        super.onResume();
    }

    public void setSituationDescription(String situation){
        situationDescription.setText(situation);

    }

}