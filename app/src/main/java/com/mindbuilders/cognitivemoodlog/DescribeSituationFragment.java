//package com.mindbuilders.cognitivemoodlog;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.app.Activity;
//import android.widget.TextView;
//
//import com.mindbuilders.cognitivemoodlog.util.utilities;
//
//
//public class DescribeSituationFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static EditText DescribeSituation;
//    DescribeSituationFragmentListener activityCommander;
//    utilities util;
//    private CreateNewLogEntryActivity parent;
//
//
//    @Override
//    public void onAttach(Activity activity){
//        super.onAttach(activity);
//        try {
//            activityCommander = (DescribeSituationFragmentListener) activity;
//
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString());
//        }
//    }
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        util=new utilities(this.getContext());
//
//        final ViewGroup rootView = (ViewGroup) inflater.inflate(
//                R.layout.fragment_describe_situation, container, false);
//        parent=(CreateNewLogEntryActivity)getActivity();
//        parent.setLeftNavInvisible();
//        DescribeSituation=(EditText) rootView.findViewById(R.id.DescribeSituationTextBox);
//
//        //todo Not quite satisfied with this method of forcing the text box to be multiline
//        if (DescribeSituation!= null)
//        {
//            DescribeSituation.setHorizontallyScrolling(false);
//            DescribeSituation.setMaxLines(4);
//        }
//        //todo remove the textwatcher
//        DescribeSituation.addTextChangedListener(textWatcher);
//        DescribeSituation.setOnFocusChangeListener(new View.OnFocusChangeListener(){
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus){
//                    util.hideKeyboard(v);
//                    activityCommander.updateSituation(DescribeSituation.getText().toString());
//                }
//
//            }
//        });
//
//
//       // activity=getActivity();
//
//
//        return rootView;
//    }
//    //TODO  Fix this listener to instead fire when you're done typing.
//    private TextWatcher textWatcher = new TextWatcher() {
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            textChanged(DescribeSituation);
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count,
//                                      int after) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    };
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//
//        super.setUserVisibleHint(isVisibleToUser);
//
//        if (isVisibleToUser && parent !=null){
//            parent.setLeftNavInvisible();
//        }
//    }
//
//    public void textChanged(TextView view) {
//   //     activityCommander.updateSituation(view.getText().toString());
//
//    }
//
//   public interface DescribeSituationFragmentListener {
//       public void updateSituation(String situation);
//   }
//
//
//
//}