package com.mindbuilders.cognitivemoodlog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    Context context;
    AlertDialog.Builder builder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        util = new utilities(this.getContext());
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_review_emotion, container, false);
        dbHelper = new CogMoodLogDatabaseHelper(this.getContext());
        context = this.getContext();
        builder = new AlertDialog.Builder(context);
        emotionReviewList = (ViewGroup) rootView.findViewById(R.id.emotionReviewList);
        finishlogentry = (Button) rootView.findViewById(R.id.openReviewFragmentButton);
        finishlogentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CreateNewLogEntry) getActivity()).getThought_cognitivedistortionobjs()==null ||
                        ((CreateNewLogEntry) getActivity()).getThought_cognitivedistortionobjs().isEmpty() ) {

                    builder.setMessage("Your log needs negative thoughts and cognitive distortions identified to continue. If you wish to start over, the menu on the top right can clear your progress")
                            .setTitle("Unfinished Log?");

                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if(((CreateNewLogEntry) getActivity()).getEmotionobjList()==null ||
                        ((CreateNewLogEntry) getActivity()).getEmotionobjList().isEmpty() ){
                    builder.setMessage("Your log needs emotions to continue. If you wish to start over, the menu on the top right can clear your progress")
                            .setTitle("Unfinished Log?");

                    builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();


                }
                else {
                    openReviewActivity(v);
                }
            }
        });
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && ((CreateNewLogEntry)getActivity()).getEmotionobjList()!=null) {
            ((CreateNewLogEntry)getActivity()).setRightNavInvisible();
            emotionobjList=((CreateNewLogEntry)getActivity()).getEmotionobjList();
            emotionReviewList.removeAllViews();
            for (emotionobj emo: emotionobjList) {
                  //  if(!emo.getIsaddedtoreview()) {
                        LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
                        View tv = inflater.inflate(R.layout.review_emotion_listitem, rootView, false);
                        SeekBar seekbar = (SeekBar) tv.findViewById(R.id.emotionreview_sb);
                        seekbar.setTag(emo.getId());
                        seekbar.setProgress(emo.getGetFeelingstrengthAfter());
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

                   //     emo.setIsaddedtoreview(true);
                    //}
            }
        }//end if

    }

    private void openReviewActivity(View view){
        ((CreateNewLogEntry)getActivity()).openReviewActivity();


    }

}
