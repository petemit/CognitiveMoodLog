package com.mindbuilders.cognitivemoodlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class PreferenceActivity extends AppCompatActivity implements SettingsFragment.LoadingIndicatorCallback {
    ProgressBar loadingindicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        loadingindicator = (ProgressBar) findViewById(R.id.loading_indicator);
        if (loadingindicator != null) {
            loadingindicator.setVisibility(View.INVISIBLE);
        }
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.preferences_toolbar);
//
//        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.preference_container, new SettingsFragment(this))
                .commit();

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        if (loadingindicator != null) {
            loadingindicator.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public void hideLoading() {
        if (loadingindicator != null) {
            loadingindicator.setVisibility(View.INVISIBLE);
        }
    }

}
