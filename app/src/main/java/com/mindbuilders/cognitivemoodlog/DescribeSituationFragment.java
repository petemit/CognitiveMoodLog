package com.mindbuilders.cognitivemoodlog;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.app.Activity;
import android.widget.TextView;


public class DescribeSituationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static EditText DescribeSituation;
    DescribeSituationFragmentListener activityCommander;
    utilities util =new utilities(this.getContext());


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            activityCommander = (DescribeSituationFragmentListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_describe_situation, container, false);

        DescribeSituation=(EditText) rootView.findViewById(R.id.DescribeSituationTextBox);

        //todo remove the textwatcher
        DescribeSituation.addTextChangedListener(textWatcher);
        DescribeSituation.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    util.hideKeyboard(v);
                    activityCommander.updateSituation(DescribeSituation.getText().toString());
                }

            }
        });


       // activity=getActivity();


        return rootView;
    }
    //TODO  Fix this listener to instead fire when you're done typing.
    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            textChanged(DescribeSituation);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void textChanged(TextView view) {
   //     activityCommander.updateSituation(view.getText().toString());

    }

   public interface DescribeSituationFragmentListener {
       public void updateSituation(String situation);
   }



}