package com.mindbuilders.cognitivemoodlog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 1/19/2017.
 */

public class utilities {
    private Context context;

    public utilities(Context context) {
        this.context = context;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void createThoughtReviewView(ViewGroup vg, List<thoughtobj> thoughtobjList, List<CognitiveDistortionobj> cogobjs) {
        for (thoughtobj tob : thoughtobjList) {
            ArrayList<thought_cognitivedistortionobj> thought_cognitivedistortionobjsList =
                    (ArrayList) tob.getThought_cognitivedistortionobjList();

            LayoutInflater inflater = LayoutInflater.from(vg.getContext());
            View tv = inflater.inflate(R.layout.thoughtrow_listitem, vg, false);
            TextView negthoughttv = (TextView) tv.findViewById(R.id.thoughtrow_negativethought_tv);
            TextView posthoughttv = (TextView) tv.findViewById(R.id.thoughtrow_positivethought_tv);
            negthoughttv.setText(tob.getNegativethought());
            posthoughttv.setText(tob.getPositivethought());
            TextView cogdist_tv = (TextView) tv.findViewById(R.id.review_cognitivedistortion_tv);
            for (thought_cognitivedistortionobj tcdo : thought_cognitivedistortionobjsList) {
                if (tcdo.getThoughtid() == tob.getId()) {
                    int cid = tcdo.getCognitivedistortionid();
                    for (CognitiveDistortionobj cog : cogobjs) {
                        if (cog.getId() == cid && cog.getId() > 0) {
                            cogdist_tv.setText(cog.getName());
                        }
                    }
                }
            }
            //todo something tells me this nasty nested thing is bad

            SeekBar neg_sb_before = (SeekBar) tv.findViewById(R.id.thoughtrow_neg_sb_before);
            SeekBar neg_sb_after = (SeekBar) tv.findViewById(R.id.thoughtrow_neg_sb_after);
            SeekBar pos_sb_before = (SeekBar) tv.findViewById(R.id.thoughtrow_pos_sb_before);


            TextView neg_sb_before_tv = (TextView) tv.findViewById(R.id.thoughtrow_neg_sb_before_tv);
            TextView neg_sb_after_tv = (TextView) tv.findViewById(R.id.thoughtrow_neg_sb_after_tv);
            TextView pos_sb_before_tv = (TextView) tv.findViewById(R.id.thoughtrow_pos_sb_before_tv);


            neg_sb_before.setProgress(tob.getNegativebeliefBefore() * 10);
            neg_sb_after.setProgress(tob.getNegativebeliefAfter() * 10);
            pos_sb_before.setProgress(tob.getPositivebeliefbefore() * 10);

            neg_sb_before.setEnabled(false);
            neg_sb_after.setEnabled(false);
            pos_sb_before.setEnabled(false);


            String s1 = (neg_sb_before_tv.getText() +
                    Integer.toString(tob.getNegativebeliefBefore()));
            String s2 = (neg_sb_after_tv.getText() +
                    Integer.toString(tob.getNegativebeliefAfter()));
            String s3 = (pos_sb_before_tv.getText() +
                    Integer.toString(tob.getPositivebeliefbefore()));

            neg_sb_before_tv.setText(s1);
            neg_sb_after_tv.setText(s2);
            pos_sb_before_tv.setText(s3);

            vg.addView(tv);

        }

    }

}


