package com.mindbuilders.cognitivemoodlog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mindbuilders.cognitivemoodlog.data.CogMoodLogDatabaseHelper;
import com.mindbuilders.cognitivemoodlog.util.utilities;


/**
 * Created by Peter on 11/7/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat {

    private String password = "";
    private String key = "";
    boolean changeResult = true;

    public SettingsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        final CheckBoxPreference pref = (CheckBoxPreference)findPreference(getString(R.string.password_protect_key));
        pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, Object o) {
                final boolean result = (boolean) o;
                if (result) {
                    changeResult = true;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    View passwordView = LayoutInflater.from(getContext()).inflate(R.layout.password_prompt, null);
                    alertDialogBuilder.setView(passwordView);
                    final EditText userInput = (EditText) passwordView.findViewById(R.id.password_editTextDialogUserInput);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    password = userInput.getText().toString();
                                    boolean canClose = password.trim().isEmpty();
                                    String key = utilities.getSha1Hex(password);
                                    CogMoodLogDatabaseHelper.passwordProtectDb(result, preference.getContext(), key);


                                    if (!canClose) {
                                        Toast.makeText(getContext(),
                                                "Database Encrypted and Password Protected!",
                                                Toast.LENGTH_LONG).show();
                                        changeResult=true;
                                        pref.setChecked(true);
                                    }
                                    else {
                                        Toast.makeText(getContext(),
                                                "Password must not be empty and must contain non-space characters",
                                                Toast.LENGTH_LONG).show();
                                        changeResult=false;
                                    }
                                }})
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    changeResult = false;
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    return false;
                } else {
                    CogMoodLogDatabaseHelper.passwordProtectDb(result, preference.getContext(), key);
                    changeResult = true;
                    pref.setChecked(false);
                }

                return changeResult;
            }
        });


    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }


}
