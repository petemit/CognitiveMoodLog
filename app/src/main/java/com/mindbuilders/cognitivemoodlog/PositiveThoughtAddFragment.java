//package com.mindbuilders.cognitivemoodlog;
//
//
//import android.support.v4.app.Fragment;
//import android.os.Bundle;
//import android.util.Log;
//import android.com.mindbuilders.cognitivemoodlog.view.LayoutInflater;
//import android.com.mindbuilders.cognitivemoodlog.view.View;
//import android.com.mindbuilders.cognitivemoodlog.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.mindbuilders.cognitivemoodlog.CmlDos.CognitiveDistortionobj;
//import com.mindbuilders.cognitivemoodlog.CmlDos.thought_cognitivedistortionobj;
//import com.mindbuilders.cognitivemoodlog.CmlDos.thoughtobj;
//import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;
//import com.mindbuilders.cognitivemoodlog.util.utilities;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Peter on 1/21/2017.
// */
//
////todo fix that problem where the cognitive distortion isn't updated if you go back and select it
//public class PositiveThoughtAddFragment extends Fragment {
//    ViewGroup rootView;
//    List<thoughtobj> thoughtobjList;
//    List<thought_cognitivedistortionobj> thought_cognitivedistortionList;
//    ViewGroup posThoughtAdderList;
//    CogMoodLogDatabaseHelper dbHelper;
//    private List<CognitiveDistortionobj> cogobjs;
//   // ThoughtAddFragment.ThoughtAddFragmentListener thoughtAddListener;
//    utilities util;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        util=new utilities(this.getContext());
//        rootView = (ViewGroup) inflater.inflate(
//                R.layout.fragment_positive_thought_add, container, false);
//        dbHelper=new CogMoodLogDatabaseHelper(this.getContext());
//        thought_cognitivedistortionList=new ArrayList<thought_cognitivedistortionobj>();
//        cogobjs=dbHelper.getCognitiveDistortionNameList();
//
////        try {
////            thoughtAddListener = (ThoughtAddFragment.ThoughtAddFragmentListener) this.getContext();
////
////        } catch (ClassCastException e) {
////            throw new ClassCastException(this.getContext().toString());
////        }
//
//        posThoughtAdderList=(ViewGroup)rootView.findViewById(R.id.posThoughtAdderList);
//        return rootView;
//
//
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//
//        super.setUserVisibleHint(isVisibleToUser);
//
//        if (isVisibleToUser && ((CreateNewLogEntryActivity)getActivity()).getThoughtobjList()!=null &&((CreateNewLogEntryActivity)getActivity()).getThought_cognitivedistortionobjs()!=null) {
//            thoughtobjList=((CreateNewLogEntryActivity)getActivity()).getThoughtobjList();
//            posThoughtAdderList.removeAllViews();
//            for (thoughtobj tob: thoughtobjList) {
//               /* if (!tob.getIsIsaddedToPosThoughtAdd()){*/
//
//                    LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
//                    View tv = inflater.inflate(R.layout.positive_thought_adder_listitem, rootView, false);
//                    SeekBar seekbar = (SeekBar) tv.findViewById(R.id.posthought_sb);
//                    seekbar.setTag(tob.getId());
//                    TextView thought = (TextView) tv.findViewById(R.id.posthoughtadder_negthought_tv);
//                    thought.setText("");
//                    thought.setText(tob.getNegativethought());
//                    thought.setTag(tob.getId()+"thought_tv");
//                    EditText posthought_et=(EditText)tv.findViewById(R.id.posthought_et);
//                    posthought_et.setMaxLines(4);
//                    posthought_et.setHorizontallyScrolling(false);
//                    posthought_et.setTag(tob.getId());
//                    posthought_et.setText(tob.getPositivethought());
//                    seekbar.setProgress(tob.getPositivebeliefbefore());
//                    thought_cognitivedistortionList=((CreateNewLogEntryActivity)this.getActivity()).getThought_cognitivedistortionobjs();
//                    //todo probably be better to convert these to hashtables or something...
//
//                    posthought_et.setOnFocusChangeListener(new View.OnFocusChangeListener(){
//                        @Override
//                        public void onFocusChange(View v, boolean hasFocus) {
//                            if (!hasFocus){
//                                util.hideKeyboard(v);
//                                for (thoughtobj tob: thoughtobjList) {
//                                    if (v.getTag()!=null&&tob.getId()==(int)v.getTag()){
//                                        tob.setPositivethought(((EditText)v).getText().toString());
//                                        Log.e("test","test2");//     thoughtAddListener.updateThoughts(thoughtobjList);
//                                    }
//                                }//endfor
//                            }
//
//                        }
//                    });
//
//                    seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                        @Override
//                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                        }
//
//                        @Override
//                        public void onStartTrackingTouch(SeekBar seekBar) {
//
//                        }
//
//                        @Override
//                        public void onStopTrackingTouch(SeekBar seekBar) {
//                            rootView.hasFocus();
//                            for (thoughtobj tob: thoughtobjList) {
//                                if (seekBar.getTag()!=null&&tob.getId()==(int)seekBar.getTag()){
//                                    tob.setPositivebeliefbefore(seekBar.getProgress());
//                                }
//
//                            }
//                        }
//                    });
//
//
//
//                    for (thought_cognitivedistortionobj tcdo :thought_cognitivedistortionList) {
//
//                        if (tcdo.getThoughtid() == tob.getId()) {
//                            int cid=tcdo.getCognitivedistortionid();
//
//                            for (CognitiveDistortionobj cog : cogobjs) {
//                                if (cog.getId() == cid && cog.getId()>0) {
//                                    //This appends the cognitive distortion to the Negative Thought Text
//                                    if (!thought.getText().toString().contains(cog.getName()))
//                                    thought.append("   (" + cog.getName() + ")");
//                                }
//                            }
//                        }
//                    }
//
//                        posThoughtAdderList.addView(tv);
//
//             /*   }//end if isaddedtoposthoughtadd*/
//                /*else{
//                  TextView textview= (TextView)rootView.findViewWithTag(tob.getId()+"thought_tv");
//
//
//                    for (thought_cognitivedistortionobj tcdo :thought_cognitivedistortionList) {
//
//                        if (tcdo.getThoughtid() == tob.getId()) {
//                            int cid=tcdo.getCognitivedistortionid();
//
//                            for (CognitiveDistortionobj cog : cogobjs) {
//                                if (cog.getId() == cid && cog.getId()>0) {
//                                    //This appends the cognitive distortion to the Negative Thought Text
//                                    textview.setText(tob.getNegativethought() +"   (" + cog.getName() + ")");
//                                }
//                            }
//                        }
//                    }
//
//                }*/
//             //   tob.setIsIsaddedToPosThoughtAdd(true);
//
//            }
//        }//end if
//
//    }
//
////    public interface PositiveThoughtAddListener {
////        public void updatePositiveThoughtAddListList(List<thoughtobj> toblist);
////
////    }
//}
//
//
