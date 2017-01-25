package com.mindbuilders.cognitivemoodlog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 1/22/2017.
 */

public class ReviewEmotionFragment extends Fragment {
    ViewGroup rootView;
    List<emotionobj> emotionobjList;
    ViewGroup emotionReviewList;
    CogMoodLogDatabaseHelper dbHelper;
    // ThoughtAddFragment.ThoughtAddFragmentListener thoughtAddListener;
    utilities util;
    Button finishlogentry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        util=new utilities(this.getContext());
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_review_emotion, container, false);
        dbHelper=new CogMoodLogDatabaseHelper(this.getContext());

        emotionReviewList=(ViewGroup)rootView.findViewById(R.id.emotionReviewList);
        finishlogentry=(Button)rootView.findViewById(R.id.openReviewFragmentButton);
        finishlogentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReviewActivity(v);
            }
        });
        return rootView;


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && ((CreateNewLogEntry)getActivity()).getEmotionobjList()!=null) {
            emotionobjList=((CreateNewLogEntry)getActivity()).getEmotionobjList();
            for (emotionobj emo: emotionobjList) {
                    if(!emo.getIsaddedtoreview()) {
                        LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
                        View tv = inflater.inflate(R.layout.review_emotion_listitem, rootView, false);
                        SeekBar seekbar = (SeekBar) tv.findViewById(R.id.emotionreview_sb);
                        seekbar.setTag(emo.getId());
                        TextView emotion = (TextView) tv.findViewById(R.id.emotionreview_tv);
                        emotion.setText(emo.getName());
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
                                for (emotionobj emo : emotionobjList) {
                                    if (seekBar.getTag() != null && emo.getId() == (int) seekBar.getTag()) {
                                        emo.setGetFeelingstrengthAfter(seekBar.getProgress());
                                    }
                                }
                            }
                        });

                        emotionReviewList.addView(tv);

                        emo.setIsaddedtoreview(true);
                    }
            }
        }//end if

    }

    private void openReviewActivity(View view){
        ((CreateNewLogEntry)getActivity()).openReviewActivity();


    }

}
