package com.mindbuilders.cognitivemoodlog;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 1/21/2017.
 */

public class PositiveThoughtAddFragment extends Fragment {
    ViewGroup rootView;
    List<thoughtobj> thoughtobjList;
    List<thought_cognitivedistortionobj> thought_cognitivedistortionList;
    ViewGroup posThoughtAdderList;
    CogMoodLogDatabaseHelper dbHelper;
    private List<CognitiveDistortionobj> cogobjs;
    PositiveThoughtAddListener mPosThoughtAddListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_positive_thought_add, container, false);
        dbHelper=new CogMoodLogDatabaseHelper(this.getContext());
        thought_cognitivedistortionList=new ArrayList<thought_cognitivedistortionobj>();
        cogobjs=dbHelper.getCognitiveDistortionNameList();

        posThoughtAdderList=(ViewGroup)rootView.findViewById(R.id.posThoughtAdderList);
        return rootView;


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && ((CreateNewLogEntry)getActivity()).getThoughtobjList()!=null &&((CreateNewLogEntry)getActivity()).getThought_cognitivedistortionobjs()!=null) {
            thoughtobjList=((CreateNewLogEntry)getActivity()).getThoughtobjList();
            for (thoughtobj tob: thoughtobjList) {
                if (!tob.getIsIsaddedToPosThoughtAdd()){

                    LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
                    View tv = inflater.inflate(R.layout.positive_thought_adder_listitem, rootView, false);

                    TextView thought = (TextView) tv.findViewById(R.id.posthoughtadder_negthought_tv);
                    thought.setText(tob.getNegativethought());
                    EditText posthought_et=(EditText)tv.findViewById(R.id.posthought_et);
                    thought_cognitivedistortionList=((CreateNewLogEntry)this.getActivity()).getThought_cognitivedistortionobjs();
                    //todo probably be better to convert these to hashtables or something...

                    for (thought_cognitivedistortionobj tcdo :thought_cognitivedistortionList) {

                        if (tcdo.getThoughtid() == tob.getId()) {
                            int cid=tcdo.getCognitivedistortionid();

                            for (CognitiveDistortionobj cog : cogobjs) {
                                if (cog.getId() == cid && cog.getId()>0) {
                                    //This appends the cognitive distortion to the Negative Thought Text
                                    thought.append("   (" + cog.getName() + ")");
                                    Log.e("PosThoughtAddFragment",cog.getName());
                                }
                            }
                        }
                    }

                        posThoughtAdderList.addView(tv);

                }
                tob.setIsIsaddedToPosThoughtAdd(true);
            }
        }//end if

    }

    public interface PositiveThoughtAddListener {
    }
}


