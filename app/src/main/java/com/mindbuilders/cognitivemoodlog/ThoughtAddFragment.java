package com.mindbuilders.cognitivemoodlog;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ThoughtAddFragment extends Fragment implements View.OnClickListener {

    TextView situationDescription;
    Button addThoughtButton;
    EditText negThoughtEditText;
    ViewGroup negthoughtlistview;
    ViewGroup rootView;
    private List<thoughtobj> thoughtobjList;
    int thoughtincrementor=1;
    ThoughtAddFragmentListener mthoughtaddListener;
    utilities util;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mthoughtaddListener = (ThoughtAddFragmentListener) activity;
        } catch(ClassCastException c){
            throw new ClassCastException(activity.toString() + " must implement ThoughtAddFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_thought_add, container, false);
        util=new utilities(this.getContext());
        situationDescription = (TextView) rootView.findViewById(R.id.ThoughtAddSituationDescription);
        addThoughtButton=(Button)rootView.findViewById(R.id.addnegthoughtbutton);
        addThoughtButton.setOnClickListener(this);
        negThoughtEditText=(EditText)rootView.findViewById(R.id.addnegthoughtedittext);
        negthoughtlistview=(ViewGroup)rootView.findViewById(R.id.negthoughtlist);
        situationDescription.setText(((CreateNewLogEntry)getActivity()).getSituation());
        negThoughtEditText.setHorizontallyScrolling(false);
        negThoughtEditText.setMaxLines(4);
        negThoughtEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    util.hideKeyboard(v);
                }

            }
        });
        setThoughtList(new ArrayList<thoughtobj>());
        // todo create a way to recreate this view when pulled from memory.  Just take the objects from the parent activity
        return rootView;
    }

    @Override
    public void onResume() {
        situationDescription.setText(((CreateNewLogEntry)getActivity()).getSituation());
        super.onResume();
    }

    public void setThoughtList(List<thoughtobj> thoughtobjList) {
        this.thoughtobjList = thoughtobjList;
    }


    public interface ThoughtAddFragmentListener {
        public void updateThoughts(List<thoughtobj> thoughtobjList);
    }

    @Override
    public void onClick(View view) {
        if (!(negThoughtEditText.getText().equals(""))
                || !(negThoughtEditText.getText()==(null))) {
        //    TextView tv = new TextView(this.getContext());
       //     tv.setText(negThoughtEditText.getText());
          //  negthoughtlistview.addView(tv);

            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            View tv = inflater.inflate(R.layout.negative_thought,rootView,false);

// fill in any details dynamically here
            TextView textView = (TextView) tv.findViewById(R.id.negthoughtlistitem);
            tv.setTag(Integer.toString(thoughtincrementor)+"tv");
            SeekBar seekbar = (SeekBar) tv.findViewById(R.id.negthoughtseekbar);
            View removebutton=(View) tv.findViewById(R.id.removenegthoughtbutton);
            removebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    negthoughtlistview.removeView(negthoughtlistview.findViewWithTag(Integer.toString(thoughtincrementor-1)+"tv"));
                    thoughtobjList.remove(thoughtincrementor-2);
                    thoughtincrementor--;

                }
            });
            textView.setTag(thoughtincrementor);
            textView.setText(negThoughtEditText.getText());
            negThoughtEditText.setText("");
            negThoughtEditText.setEnabled(false);
            negThoughtEditText.setEnabled(true);
            seekbar.setTag(thoughtincrementor);

            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    for (thoughtobj tob: getThoughtList()) {
                        if (seekBar.getTag()!=null&&tob.getId()==(int)seekBar.getTag()){
                            tob.setNegativebeliefBefore(seekBar.getProgress());

                        }

                    }
                }
            });

// insert into main view
            //rootView.addView(tv);
            //Create thought object and add it to the thoughtobject list
            thoughtobj thoughtobj=new thoughtobj();
            thoughtobj.setId(thoughtincrementor);
            thoughtobj.setNegativethought(textView.getText().toString());
            thoughtobjList.add(thoughtobj);
            thoughtincrementor++;
            negthoughtlistview.addView(tv);
            mthoughtaddListener.updateThoughts(thoughtobjList);
        }

    }

    public List<thoughtobj> getThoughtList(){
        return thoughtobjList;
    }
    public void setThoughtAddSituationDescription(String situation){
        situationDescription.setText(situation);

    }

}

