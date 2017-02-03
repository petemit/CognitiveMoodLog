package com.mindbuilders.cognitivemoodlog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 1/22/2017.
 */

public class ReviewNegativeThoughtFragment extends Fragment {
    ViewGroup rootView;
    List<thoughtobj> thoughtobjList;
    List<thought_cognitivedistortionobj> thought_cognitivedistortionList;
    ViewGroup negThoughtReviewList;
    CogMoodLogDatabaseHelper dbHelper;
    private List<CognitiveDistortionobj> cogobjs;
    // ThoughtAddFragment.ThoughtAddFragmentListener thoughtAddListener;
    utilities util;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        util=new utilities(this.getContext());
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_review_negthought, container, false);
        dbHelper=new CogMoodLogDatabaseHelper(this.getContext());
        thought_cognitivedistortionList=new ArrayList<thought_cognitivedistortionobj>();
        cogobjs=dbHelper.getCognitiveDistortionNameList();

        negThoughtReviewList=(ViewGroup)rootView.findViewById(R.id.negthoughtReviewList);
        return rootView;


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && ((CreateNewLogEntry)getActivity()).getThoughtobjList()!=null &&
                ((CreateNewLogEntry)getActivity()).getThought_cognitivedistortionobjs()!=null) {
            thoughtobjList=((CreateNewLogEntry)getActivity()).getThoughtobjList();
            negThoughtReviewList.removeAllViews();
            for (thoughtobj tob: thoughtobjList) {
             /*   if (!tob.getIsNegThoughtReviewDone()){*/

                    LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
                    View tv = inflater.inflate(R.layout.review_negthought_listitem, rootView, false);
                    SeekBar seekbar = (SeekBar) tv.findViewById(R.id.negthoughtreview_sb);
                    seekbar.setTag(tob.getId());
                    TextView negthought = (TextView) tv.findViewById(R.id.negthoughtreview_tv);
                    negthought.setText("");
                    negthought.setText(tob.getNegativethought());
                    negthought.setTag(tob.getId()+"negthought_tv");
                    TextView posthought=(TextView) tv.findViewById(R.id.posthoughtreview_tv);
                    posthought.setText(tob.getPositivethought());
                    seekbar.setProgress(tob.getNegativebeliefAfter());
                    thought_cognitivedistortionList=((CreateNewLogEntry)this.getActivity()).getThought_cognitivedistortionobjs();
                    //todo probably be better to convert these to hashtables or something...

                    seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            rootView.hasFocus();
                            for (thoughtobj tob: thoughtobjList) {
                                if (seekBar.getTag()!=null&&tob.getId()==(int)seekBar.getTag()){
                                    tob.setNegativebeliefAfter(seekBar.getProgress());
                                }

                            }
                        }
                    });


                    for (thought_cognitivedistortionobj tcdo :thought_cognitivedistortionList) {

                        if (tcdo.getThoughtid() == tob.getId()) {
                            int cid=tcdo.getCognitivedistortionid();

                            for (CognitiveDistortionobj cog : cogobjs) {
                                if (cog.getId() == cid && cog.getId()>0) {
                                    //This appends the cognitive distortion to the Negative Thought Text
                                    if (!negthought.getText().toString().contains(cog.getName()))
                                    negthought.append("   (" + cog.getName() + ")");
                                }
                            }
                        }
                    }

                    negThoughtReviewList.addView(tv);

                /*}//endif tob is added*/
             /*   else{
                    TextView textview= (TextView)rootView.findViewWithTag(tob.getId()+"negthought_tv");


                    for (thought_cognitivedistortionobj tcdo :thought_cognitivedistortionList) {

                        if (tcdo.getThoughtid() == tob.getId()) {
                            int cid=tcdo.getCognitivedistortionid();

                            for (CognitiveDistortionobj cog : cogobjs) {
                                if (cog.getId() == cid && cog.getId()>0) {
                                    //This appends the cognitive distortion to the Negative Thought Text
                                    textview.setText(tob.getNegativethought() +"   (" + cog.getName() + ")");
                                }
                            }
                        }
                    }

                }
                tob.setIsNegThoughtReviewDone(true);*/
            }
        }//end if

    }

}




