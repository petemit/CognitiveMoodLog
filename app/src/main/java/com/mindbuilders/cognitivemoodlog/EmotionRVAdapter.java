package com.mindbuilders.cognitivemoodlog;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Peter on 1/2/2017.
 */

public class EmotionRVAdapter extends RecyclerView.Adapter<EmotionRVAdapter.EmotionViewHolder> {

    private int emotioncategorycount=0;
    private Cursor cursor;
    SQLiteDatabase db;
    private Context context;
    private List<emotionobj> emotionList;
    EmotionRVAdapterListener mEmotionAddListener;
    Activity activity;

    public EmotionRVAdapter (int i, Cursor cursor, SQLiteDatabase db, Activity activity){
        this.cursor=cursor;
        this.db =db;
        this.activity=activity;
        emotioncategorycount=i;
    }

    public interface EmotionRVAdapterListener {
        void updateEmotions(List<emotionobj> emotionList);
    }

    @Override
    public EmotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context =parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.emotion_listitem,parent,false);
        EmotionViewHolder viewHolder=new EmotionViewHolder(view);
        setEmotionList(((CreateNewLogEntry)activity).getEmotionobjList());
        try {
            mEmotionAddListener = (EmotionRVAdapterListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EmotionViewHolder holder, int position) {
    holder.bind(position);
       // super.bindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return emotioncategorycount;
    }

    public List<emotionobj> getEmotionList() {
        return emotionList;
    }

    public void setEmotionList(List<emotionobj> emotionList) {
        this.emotionList = emotionList;
    }

    class EmotionViewHolder extends RecyclerView.ViewHolder {
        TextView listItemTextView;
        RadioGroup listItemRadioGroup;
        SeekBar seekbar;

        public EmotionViewHolder(final View itemView) {
            super(itemView);
            listItemTextView=(TextView) itemView.findViewById(R.id.emotioncategoryitem);
            listItemRadioGroup=(RadioGroup) itemView.findViewById(R.id.emotionradiogroup);
            seekbar=(SeekBar)itemView.findViewById(R.id.emotionstrengthseekbar);

            listItemRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    emotionobj emo = new emotionobj();
                    emo.setId(checkedId);
                    emo.setName(((RadioButton)(group.findViewById(group.getCheckedRadioButtonId()))).getText().toString());


                    seekbar.setTag(checkedId);
                    getEmotionList().add(emo);

                }
            });

            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    for (emotionobj emo: getEmotionList()) {
                        if (seekBar.getTag()!=null&&emo.getId()==(int)seekBar.getTag()){
                            emo.setFeelingstrengthBefore(seekBar.getProgress());

                        }

                    }
                }
            });


        }

        public void bind(int position) {


            if (cursor.moveToPosition(position))
            {
                listItemTextView.setText(cursor.getString(cursor.getColumnIndex("name")));



                String[] projection = {
                        "rowid",
                        CogMoodLogDatabaseContract.emotion.COLUMN_EMOTIONCATEGORY_ID,
                        CogMoodLogDatabaseContract.emotion.COLUMN_NAME
                };
                String wherecol=CogMoodLogDatabaseContract.emotion.COLUMN_EMOTIONCATEGORY_ID + " = ?";
                String[] whereargs = {
                        cursor.getString(cursor.getColumnIndex("rowid"))
                };

                String sortOrder =
                        CogMoodLogDatabaseContract.emotion.COLUMN_NAME + " DESC";
                Cursor groupcursor= db.query(
                        CogMoodLogDatabaseContract.emotion.TABLE_NAME,                     // The table to query
                        projection,                               // The columns to return
                        wherecol,                                // The columns for the WHERE clause
                        whereargs,                            // The values for the WHERE clause
                        null,                                     // don't group the rows
                        null,                                     // don't filter by row groups
                        sortOrder                                 // The sort order
                );
                listItemRadioGroup.removeAllViews();
             //   listItemRadioGroup=(ViewGroup) itemView.findViewById(R.id.emotionradiogroup);
                    while (groupcursor.moveToNext()) {
                        RadioButton rb = new RadioButton(itemView.getContext());//listItemRadioGroup.getContext());//itemView.getContext());
                        rb.setText(groupcursor.getString(groupcursor.getColumnIndex("name")));
                        if ( groupcursor.getString(groupcursor.getColumnIndex("rowid")) != null) {
                            rb.setId(Integer.parseInt(groupcursor.getString(groupcursor.getColumnIndex("rowid"))));
                        }
                        listItemRadioGroup.addView(rb);
                    }
            }

        }
    }

}
