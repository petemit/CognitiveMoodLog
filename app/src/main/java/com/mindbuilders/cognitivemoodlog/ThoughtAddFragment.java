//package com.mindbuilders.cognitivemoodlog;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.com.mindbuilders.cognitivemoodlog.view.LayoutInflater;
//import android.com.mindbuilders.cognitivemoodlog.view.View;
//import android.com.mindbuilders.cognitivemoodlog.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.mindbuilders.cognitivemoodlog.CmlDos.thoughtobj;
//import com.mindbuilders.cognitivemoodlog.util.utilities;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ThoughtAddFragment extends Fragment implements View.OnClickListener {
//
//    TextView situationDescription;
//    Button addThoughtButton;
//    EditText negThoughtEditText;
//    ViewGroup negthoughtlistview;
//    ViewGroup rootView;
//    int thoughtincrementor=1;
//    ThoughtAddFragmentListener mthoughtaddListener;
//    utilities util;
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mthoughtaddListener = (ThoughtAddFragmentListener) activity;
//        } catch(ClassCastException c){
//            throw new ClassCastException(activity.toString() + " must implement ThoughtAddFragmentListener");
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        rootView = (ViewGroup) inflater.inflate(
//                R.layout.fragment_thought_add, container, false);
//        util=new utilities(this.getContext());
//        situationDescription = rootView.findViewById(R.id.ThoughtAddSituationDescription);
//        addThoughtButton= rootView.findViewById(R.id.addnegthoughtbutton);
//        addThoughtButton.setOnClickListener(this);
//        negThoughtEditText= rootView.findViewById(R.id.addnegthoughtedittext);
//        negthoughtlistview= rootView.findViewById(R.id.negthoughtlist);
//        situationDescription.setText(((CreateNewLogEntryActivity)getActivity()).getSituation());
//        negThoughtEditText.setHorizontallyScrolling(false);
//        negThoughtEditText.setMaxLines(4);
//        negThoughtEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus){
//                    util.hideKeyboard(v);
//                }
//
//            }
//        });
//
//            handleList();
//
//
//        // todo create a way to recreate this com.mindbuilders.cognitivemoodlog.view when pulled from memory.  Just take the objects from the parent activity
//        return rootView;
//    }
//
//    private void handleList() {
//        if (((CreateNewLogEntryActivity) getActivity()).getThoughtobjList() == null) {
//            BaseApplication.thoughtobjs = new ArrayList<>();
//        }
//        else {
//            negthoughtlistview.removeAllViews();
//            for (int i = 0; i < (((CreateNewLogEntryActivity) getActivity()).getThoughtobjList().size()); i++) {
//                thoughtobj to = ((CreateNewLogEntryActivity) getActivity()).getThoughtobjList().get(i);
//                LayoutInflater inflater = LayoutInflater.from(getContext());
//                View tv = inflater.inflate(R.layout.negative_thought,rootView,false);
//
//
//
//                TextView textView = (TextView) tv.findViewById(R.id.negthoughtlistitem);
//                tv.setTag(Integer.toString(to.getId())+"tv");
//                SeekBar seekbar = (SeekBar) tv.findViewById(R.id.negthoughtseekbar);
//                View removebutton=(View) tv.findViewById(R.id.removenegthoughtbutton);
//                removebutton.setOnClickListener(new RemoveThoughtClickListener(to, to.getId()));
//                textView.setText(to.getNegativethought());
//                textView.setTag(to.getId());
//                seekbar.setTag(to.getId());
//                seekbar.setOnSeekBarChangeListener(new MySeekBarChangeListener());
//                seekbar.setProgress(to.getNegativebeliefBefore());
//
//                negthoughtlistview.addView(tv);
//            }
//        }
//
//    }
//
//    class RemoveThoughtClickListener implements View.OnClickListener {
//        thoughtobj to;
//        int index;
//        public RemoveThoughtClickListener(thoughtobj to, int index) {
//            this.to = to;
//            this.index = index;
//        }
//        @Override
//        public void onClick(View v) {
//            negthoughtlistview.removeView(negthoughtlistview.findViewWithTag(Integer.toString(index)+"tv"));
//            ArrayList<thoughtobj> list = (ArrayList)((CreateNewLogEntryActivity) getActivity()).getThoughtobjList();
//            list.remove(index);
//            for (int i = 0; i < (list.size()); i++) {
//                list.get(i).setId(list.indexOf(list.get(i)));
//                negthoughtlistview.getChildAt(i).setTag(Integer.toString(to.getId())+"tv");
//                TextView textView = (TextView)  negthoughtlistview.getChildAt(i).findViewById(R.id.negthoughtlistitem);
//                SeekBar seekbar = (SeekBar)  negthoughtlistview.getChildAt(i).findViewById(R.id.negthoughtseekbar);
//                View removebutton=(View)  negthoughtlistview.getChildAt(i).findViewById(R.id.removenegthoughtbutton);
//                removebutton.setOnClickListener(new RemoveThoughtClickListener(to, to.getId()));
//                textView.setTag(to.getId());
//                seekbar.setTag(to.getId());
//                seekbar.setOnSeekBarChangeListener(new MySeekBarChangeListener());
//            }
//
//            thoughtincrementor--;
//        }
//    }
//
//
//    @Override
//    public void onResume() {
//        situationDescription.setText(((CreateNewLogEntryActivity)getActivity()).getSituation());
//        handleList();
//        super.onResume();
//
//    }
//
//
//    public interface ThoughtAddFragmentListener {
//        public void updateThoughts(List<thoughtobj> thoughtobjList);
//        List<thoughtobj> getThoughtList();
//    }
//
//    @Override
//    public void onClick(View com.mindbuilders.cognitivemoodlog.view) {
//        if (!(negThoughtEditText.getText().equals(""))
//                || !(negThoughtEditText.getText()==(null))) {
//        //    TextView tv = new TextView(this.getContext());
//       //     tv.setText(negThoughtEditText.getText());
//          //  negthoughtlistview.addView(tv);
//
//            LayoutInflater inflater = LayoutInflater.from(com.mindbuilders.cognitivemoodlog.view.getContext());
//            View tv = inflater.inflate(R.layout.negative_thought,rootView,false);
//
//// fill in any details dynamically here
//            TextView textView = (TextView) tv.findViewById(R.id.negthoughtlistitem);
//
//            SeekBar seekbar = (SeekBar) tv.findViewById(R.id.negthoughtseekbar);
//            View removebutton=(View) tv.findViewById(R.id.removenegthoughtbutton);
//
//
//            textView.setText(negThoughtEditText.getText());
//            negThoughtEditText.setText("");
//            negThoughtEditText.setEnabled(false);
//            negThoughtEditText.setEnabled(true);
//
//
//            seekbar.setOnSeekBarChangeListener(new MySeekBarChangeListener());
//
//// insert into main com.mindbuilders.cognitivemoodlog.view
//            //rootView.addView(tv);
//            //Create thought object and add it to the thoughtobject list
//            thoughtobj to=new thoughtobj();
//
//            to.setNegativethought(textView.getText().toString());
//
//            ((CreateNewLogEntryActivity) getActivity()).getThoughtobjList().add(to);
//            to.setId(((CreateNewLogEntryActivity) getActivity()).getThoughtobjList().indexOf(to));
//            removebutton.setOnClickListener(new RemoveThoughtClickListener(to, to.getId()));
//            tv.setTag(Integer.toString(to.getId())+"tv");
//            textView.setTag(to.getId());
//            seekbar.setTag(to.getId());
//            thoughtincrementor++;
//            negthoughtlistview.addView(tv);
//        }
//
//    }
//    public void setThoughtAddSituationDescription(String situation){
//        situationDescription.setText(situation);
//
//    }
//
//    class MySeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
//
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            for (thoughtobj tob: ((CreateNewLogEntryActivity) getActivity()).getThoughtobjList()) {
//                if (seekBar.getTag()!=null&&tob.getId()==(int)seekBar.getTag()){
//                    tob.setNegativebeliefBefore(seekBar.getProgress());
//
//                }
//
//            }
//        }
//    }
//
//}
//
