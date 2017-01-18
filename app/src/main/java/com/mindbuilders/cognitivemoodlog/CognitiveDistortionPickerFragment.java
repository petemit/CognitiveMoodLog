package com.mindbuilders.cognitivemoodlog;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Peter on 1/15/2017.
 */

public class CognitiveDistortionPickerFragment extends Fragment {
    ViewGroup rootView;
    List<thoughtobj> thoughtobjList;
    List<thought_cognitivedistortionobj> thought_cognitivedistortionList;
    ViewGroup negThoughtListCogDistortion;

    private Cursor cursor;
    SQLiteDatabase db;
    private Context context;
    private List<String> coglist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_cognitive_picker, container, false);

        negThoughtListCogDistortion=(ViewGroup)rootView.findViewById(R.id.negThoughtListCogDistortion);
        return rootView;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Log.e("CogDistoPickerFragment","got here man");
            thoughtobjList=((CreateNewLogEntry)getActivity()).getThoughtobjList();
            for (thoughtobj tob: thoughtobjList) {
                LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
                View tv = inflater.inflate(R.layout.negthought_listitem,rootView,false);

                TextView thought=(TextView)tv.findViewById(R.id.negthoughtcogdistlistitem_tv);
                Spinner spin=(Spinner)tv.findViewById(R.id.cogdistortion_spinner);
                thought.setText(tob.getNegativethought());
                negThoughtListCogDistortion.addView(tv);




            }
        }
    }


    public List<String> getCoglist() {
        return coglist;
    }

    public void setCoglist(List<String> coglist) {
        this.coglist = coglist;
    }
}
