package com.mindbuilders.cognitivemoodlog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.TabPageIndicator;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class CreateNewLogEntry extends AppCompatActivity implements DescribeSituationFragment.DescribeSituationFragmentListener, ThoughtAddFragment.ThoughtAddFragmentListener,EmotionRVAdapter.EmotionRVAdapterListener, CognitiveDistortionPickerFragment.CognitiveDistortionPickerListener{
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
    private static final String[] CONTENT = new String[] { "Situation", "Emotions", "Negative Thoughts", "Cognitive Distortions", "Positive Thoughts", "Review Thoughts", "Review Emotions" };


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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cognitive Mood Log");
       // toolbar.setTitleTextColor(Color.WHITE);
    /*    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });*/
        setSupportActionBar(toolbar);


        //TODO this is gross still.. probably should use a fragment, but actually... not a bad solution to just get the text from the parent activity, you need to make the situation description its own fragment that you can nest inside the child fragments
        mPager.setOffscreenPageLimit(7);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

//        //Binding the title view pager indicator
        TabPageIndicator2 tabPageIndicator2 = (TabPageIndicator2) findViewById(R.id.vpi);
        tabPageIndicator2.setViewPager(mPager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_log_entry_menuitem:
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateNewLogEntry.this);

// 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("If you delete this entry, all your entered data will be lost!")
                        .setTitle("Are you sure?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(CreateNewLogEntry.this, MainActivity.class);
                        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

// 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_new_log_entry, menu);
        return true;
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
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length];
        }


        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void openReviewActivity(){

        Intent myIntent = new Intent(this, ReviewActivity.class);
        myIntent.putExtra("emotionobjList",(ArrayList)getEmotionobjList());
        myIntent.putExtra("thoughtobjList",(ArrayList)getThoughtobjList());
        myIntent.putExtra("thought_cognitivedistortionobj",(ArrayList)getThought_cognitivedistortionobjs());
        myIntent.putExtra("situation",this.getSituation());

        startActivity(myIntent);

    }

}
