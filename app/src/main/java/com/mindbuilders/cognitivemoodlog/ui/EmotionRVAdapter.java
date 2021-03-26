//package com.mindbuilders.cognitivemoodlog.ui;
//
//import android.app.Activity;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.widget.RecyclerView;
//import android.com.mindbuilders.cognitivemoodlog.view.LayoutInflater;
//import android.com.mindbuilders.cognitivemoodlog.view.View;
//import android.com.mindbuilders.cognitivemoodlog.view.ViewGroup;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.mindbuilders.cognitivemoodlog.BaseApplication;
//import com.mindbuilders.cognitivemoodlog.CmlDos.emotionobj;
//import com.mindbuilders.cognitivemoodlog.CreateNewLogEntryActivity;
//import com.mindbuilders.cognitivemoodlog.R;
//import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseContract;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Peter on 1/2/2017.
// */
//
//public class EmotionRVAdapter extends RecyclerView.Adapter<EmotionRVAdapter.EmotionViewHolder> {
//
//    private int emotioncategorycount=0;
//    private Cursor cursor;
//    net.sqlcipher.database.SQLiteDatabase db;
//    private Context context;
//    EmotionRVAdapterListener mEmotionAddListener;
//    Activity activity;
//
//    public EmotionRVAdapter (int i, Cursor cursor, net.sqlcipher.database.SQLiteDatabase db, Activity activity){
//        this.cursor=cursor;
//        this.db =db;
//        this.activity=activity;
//        emotioncategorycount=i;
//    }
//
//    public interface EmotionRVAdapterListener {
//        void updateEmotions(List<emotionobj> emotionList);
//    }
//
//    @Override
//    public EmotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        context =parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View com.mindbuilders.cognitivemoodlog.view = inflater.inflate(R.layout.emotion_listitem,parent,false);
//        EmotionViewHolder viewHolder=new EmotionViewHolder(com.mindbuilders.cognitivemoodlog.view);
//
//        try {
//            mEmotionAddListener = (EmotionRVAdapterListener) context;
//
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString());
//        }
//
//
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(EmotionViewHolder holder, int position) {
//    holder.bind(position);
//       // super.bindViewHolder(holder, position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return emotioncategorycount;
//    }
//
//
//    class EmotionViewHolder extends RecyclerView.ViewHolder {
//        TextView listItemTextView;
//        RadioGroup listItemRadioGroup;
//        SeekBar seekbar;
//
//        public EmotionViewHolder(final View itemView) {
//            super(itemView);
//            listItemTextView=(TextView) itemView.findViewById(R.id.emotioncategoryitem);
//            listItemTextView.setId(getAdapterPosition());
//            listItemRadioGroup=(RadioGroup) itemView.findViewById(R.id.emotionradiogroup);
//            listItemRadioGroup.setId(getAdapterPosition());
//            seekbar=(SeekBar)itemView.findViewById(R.id.emotionstrengthseekbar);
//            seekbar.setId(getAdapterPosition());
//
//            listItemRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    //todo link this to the emotion category so if you get another click in the same category it doesn't add it twice.
//                    String name = ((RadioButton) (group.findViewById(group.getCheckedRadioButtonId()))).getText().toString();
//                    String categoryId = String.valueOf((group.findViewById(group.getCheckedRadioButtonId())).getTag());
//                    emotionobj emo = findEmotionObjByNameAndCatId(name, categoryId);
//                    if (emo == null) {
//                        emo = new emotionobj();
//                    }
//                    emo.setId(checkedId);
//                    emo.setName(name);
//                    emo.setEmotioncategoryid(Integer.parseInt(categoryId));
//                    seekbar.setTag(checkedId);
//
//                    if (BaseApplication.emotionobjs.isEmpty())
//                    {
//                        BaseApplication.emotionobjs.add(emo);
//                    }
//                    int index=0;
//                  //  ArrayList<emotionobj> replacementlist = new ArrayList<emotionobj>(BaseApplication.emotionobjs);
//                    RadioButton tagbutton= ((RadioButton) (group.findViewById(group.getCheckedRadioButtonId())));
//                    int tagint = (Integer)tagbutton.getTag();
//                    for (emotionobj emoobj: BaseApplication.emotionobjs) {
//                    if(emoobj.getEmotioncategoryid()==tagint){
//                        BaseApplication.emotionobjs.set(index,emo);
//                        emo.setIsaddedtoreview(true);
//
//                    }
//                        else{
//                        //getEmotionList().add(emo);
//                    }
//                        index++;
//                   }
//                    if (!emo.getIsaddedtoreview()){
//                        BaseApplication.emotionobjs.add(emo);
//                    }
//
//
//
//                }
//            });
//
//            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
//
//                }
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
//                    for (emotionobj emo: BaseApplication.emotionobjs) {
//                        if (seekBar.getTag()!=null&&emo.getId()==(int)seekBar.getTag()){
//                            emo.setFeelingstrengthBefore(seekBar.getProgress());
//
//                        }
//
//                    }
//                }
//            });
//
//
//        }
//
//        public void bind(int position) {
//
//
//            if (cursor.moveToPosition(position))
//            {
//                listItemTextView.setText(cursor.getString(cursor.getColumnIndex("name")));
//
//
//
//                String[] projection = {
//                        "rowid",
//                        CogMoodLogDatabaseContract.emotion.COLUMN_EMOTIONCATEGORY_ID,
//                        CogMoodLogDatabaseContract.emotion.COLUMN_NAME
//                };
//                String wherecol=CogMoodLogDatabaseContract.emotion.COLUMN_EMOTIONCATEGORY_ID + " = ?";
//                String[] whereargs = {
//                        cursor.getString(cursor.getColumnIndex("rowid"))
//                };
//
//                String sortOrder =
//                        CogMoodLogDatabaseContract.emotion.COLUMN_NAME + " DESC";
//                Cursor groupcursor= db.query(
//                        CogMoodLogDatabaseContract.emotion.TABLE_NAME,                     // The table to query
//                        projection,                               // The columns to return
//                        wherecol,                                // The columns for the WHERE clause
//                        whereargs,                            // The values for the WHERE clause
//                        null,                                     // don't group the rows
//                        null,                                     // don't filter by row groups
//                        sortOrder                                 // The sort order
//                );
//                listItemRadioGroup.removeAllViews();
//             //   listItemRadioGroup=(ViewGroup) itemView.findViewById(R.id.emotionradiogroup);
//                    while (groupcursor.moveToNext()) {
//                        RadioButton rb = new RadioButton(itemView.getContext());//listItemRadioGroup.getContext());//itemView.getContext());
//                        String name = groupcursor.getString(groupcursor.getColumnIndex("name"));
//                        String categoryId = groupcursor.getString(groupcursor.getColumnIndex(CogMoodLogDatabaseContract.emotion.COLUMN_EMOTIONCATEGORY_ID));
//                        rb.setText(name);
//                        if ( groupcursor.getString(groupcursor.getColumnIndex("rowid")) != null) {
//                            rb.setId(Integer.parseInt(groupcursor.getString(groupcursor.getColumnIndex("rowid"))));
//                            rb.setTag(Integer.parseInt(categoryId));
//
//
//                        }
//                        listItemRadioGroup.addView(rb);
//                        emotionobj emo = findEmotionObjByNameAndCatId(name,categoryId);
//
//                        if (emo != null) {
//                            seekbar.setProgress(emo.getFeelingStrengthBefore());
//                            rb.toggle();
//                        }
//                    }
//            }
//
//        }
//    }
//
//    public emotionobj findEmotionObjById(int id) {
//        for (emotionobj emo: BaseApplication.emotionobjs
//             ) {
//            if (emo.getId() == id) {
//                return emo;
//            }
//        }
//        return null;
//    }
//
//    public emotionobj findEmotionObjByNameAndCatId(String name, String catId) {
//        for (emotionobj emo: BaseApplication.emotionobjs
//                ) {
//            if ((emo.getName()+emo.getEmotioncategoryid()).equals(name+catId)) {
//                return emo;
//            }
//        }
//        return null;
//    }
//
//}
