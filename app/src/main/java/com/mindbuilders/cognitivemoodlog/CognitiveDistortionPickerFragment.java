package com.mindbuilders.cognitivemoodlog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mindbuilders.cognitivemoodlog.CmlDos.CognitiveDistortionobj;
import com.mindbuilders.cognitivemoodlog.CmlDos.thought_cognitivedistortionobj;
import com.mindbuilders.cognitivemoodlog.CmlDos.thoughtobj;
import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 1/15/2017.
 */

public class CognitiveDistortionPickerFragment extends Fragment {
    ViewGroup rootView;
    List<thoughtobj> thoughtobjList;
    List<thought_cognitivedistortionobj> thought_cognitivedistortionList;
    ViewGroup negThoughtListCogDistortion;
    CogMoodLogDatabaseHelper dbHelper;
    private List<CognitiveDistortionobj> cogobjs;
    CognitiveDistortionPickerListener mCogDistPickListener;
    int selectedid=-1;
    int tcogincrementor=1;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCogDistPickListener = (CognitiveDistortionPickerListener) activity;
        } catch(ClassCastException c){
            throw new ClassCastException(activity.toString() + " must implement CognitiveDistortionPickerListener");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_cognitive_picker, container, false);
        dbHelper=new CogMoodLogDatabaseHelper(this.getContext());
        thought_cognitivedistortionList=new ArrayList<thought_cognitivedistortionobj>();
        cogobjs=dbHelper.getCognitiveDistortionNameList();

        negThoughtListCogDistortion=(ViewGroup)rootView.findViewById(R.id.negThoughtListCogDistortion);
        return rootView;


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && ((CreateNewLogEntryActivity)getActivity()).getThoughtobjList()!=null) {
            thoughtobjList=((CreateNewLogEntryActivity)getActivity()).getThoughtobjList();
            negThoughtListCogDistortion.removeAllViews();
            for (thoughtobj tob: thoughtobjList) {
           //     if (!tob.getisIsaddedToCogPicker()) {
                    LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
                    View tv = inflater.inflate(R.layout.negthought_listitem, rootView, false);

                    TextView thought = (TextView) tv.findViewById(R.id.negthoughtcogdistlistitem_tv);
                    Spinner spin = (Spinner) tv.findViewById(R.id.cogdistortion_spinner);
                    TextView desc = (TextView) tv.findViewById(R.id.cogdescription);

                    desc.setId(tob.getId());
                    spin.setTag(desc.getId());
                    thought.setText(tob.getNegativethought());
                    ArrayAdapter<CognitiveDistortionobj> adapter = new ArrayAdapter<CognitiveDistortionobj>(
                            this.getContext(), android.R.layout.simple_spinner_item, cogobjs);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin.setAdapter(adapter);


                    spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                CognitiveDistortionobj cogobj = (CognitiveDistortionobj) ((Spinner) parent).getItemAtPosition(position);
                                TextView desc = (TextView) rootView.findViewById((int) parent.getTag());
                                desc.setText(cogobj.getDescription());

                                if(cogobj.getId()!=-1) {
                                    thought_cognitivedistortionobj tcog=new thought_cognitivedistortionobj();
                                    tcog.setThoughtid((int) parent.getTag());
                                    thoughtobj tob=new thoughtobj();
                                    for (thoughtobj inntob: thoughtobjList){
                                        if (inntob.getId()==(Integer)parent.getTag()) {
                                            tob = inntob;
                                        }
                                    }
                                    tob.setSelectCogPosition(position);
                                    tcog.setCognitivedistortionid(cogobj.getId());
                                    tcog.setId(tcogincrementor);
                                    tcogincrementor++;
                                    thought_cognitivedistortionList.add(tcog);
                                    mCogDistPickListener.updateThought_CognitiveDistortionList(thought_cognitivedistortionList);

                                    int index=0;
                                    for (thought_cognitivedistortionobj tcdo : thought_cognitivedistortionList) {
                                        if (tcdo.getThoughtid()==(int)parent.getTag()){
                                            thought_cognitivedistortionList.set(index,tcog);
                                        }
                                        index++;
                                    }

                                }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    spin.setSelection(tob.getSelectCogPosition());
                    desc.setText(((CognitiveDistortionobj) spin.getSelectedItem()).getDescription());
                    negThoughtListCogDistortion.addView(tv);
                    tob.setisIsaddedToCogPicker(true);
           //     }  If is added to thingy


            }
        }
    }

    public interface CognitiveDistortionPickerListener {
        public void updateThought_CognitiveDistortionList(List<thought_cognitivedistortionobj> tcoglist);
    }

    public List<CognitiveDistortionobj> getCoglist() {
        return cogobjs;
    }

    public void setCoglist(List<CognitiveDistortionobj> cogobjs) {
        this.cogobjs = cogobjs;
    }
}
