package com.mindbuilders.cognitivemoodlog;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.LinePageIndicator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class CreateNewLogEntry extends FragmentActivity implements DescribeSituationFragment.DescribeSituationFragmentListener, ThoughtAddFragment.ThoughtAddFragmentListener,EmotionRVAdapter.EmotionRVAdapterListener, CognitiveDistortionPickerFragment.CognitiveDistortionPickerListener{
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 7;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    private List<emotionobj> emotionobjList;
    private List<thoughtobj> thoughtobjList;
    String logentry="";
    boolean isfinished;
    Timestamp timestamp;
    private List<thought_cognitivedistortionobj> thought_cognitivedistortionobjs;


    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private SmartFragmentStatePagerAdapter mPagerAdapter;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_log_entry);
        emotionobjList=new ArrayList<emotionobj>();
        mPager = (ViewPager) findViewById(R.id.viewpager);

        //TODO this is gross still.. probably should use a fragment, but actually... not a bad solution to just get the text from the parent activity, you need to make the situation description its own fragment that you can nest inside the child fragments
        mPager.setOffscreenPageLimit(10);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

//        //Binding the title view pager indicator
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.vpi);
        circlePageIndicator.setViewPager(mPager);

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void updateSituation(String situation) {
        ((EmotionPickerFragment)mPagerAdapter.getRegisteredFragment(1)).setSituationDescription(situation);
        ((ThoughtAddFragment)mPagerAdapter.getRegisteredFragment(2)).setThoughtAddSituationDescription(situation);
        //(mPagerAdapter.getRegisteredFragment(2)).toString();
        logentry=situation;
    }

    public String getSituation(){
        return logentry;
    }

    @Override
    public void updateThoughts(List<thoughtobj> thoughtobjList) {
        this.thoughtobjList=thoughtobjList;
    }

    @Override
    public void updateEmotions(List<emotionobj> emotionList) {
        this.emotionobjList=emotionList;
    }

    public List<emotionobj> getEmotionobjList() {
        return emotionobjList;
    }

    public List<thoughtobj> getThoughtobjList() {
        return thoughtobjList;
    }

    @Override
    public void updateThought_CognitiveDistortionList(List<thought_cognitivedistortionobj> tcoglist) {
        this.thought_cognitivedistortionobjs=tcoglist;
    }

    public List<thought_cognitivedistortionobj> getThought_cognitivedistortionobjs() {
        return thought_cognitivedistortionobjs;
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends SmartFragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                        return new DescribeSituationFragment();
                case 1:
                        return new EmotionPickerFragment();
                case 2:
                        return new ThoughtAddFragment();
                case 3:
                        return new CognitiveDistortionPickerFragment();
                case 4:
                        return new PositiveThoughtAddFragment();
                case 5:
                        return new ReviewNegativeThoughtFragment();
                case 6:
                        return new ReviewEmotionFragment();

                default:
                    return null;
            }

        }


        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
