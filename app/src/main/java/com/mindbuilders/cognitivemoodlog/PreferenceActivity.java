//package com.mindbuilders.cognitivemoodlog;
//
//import android.accounts.AccountManager;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ProgressBar;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import java.util.List;
//
//import pub.devrel.easypermissions.EasyPermissions;
//
//import static com.mindbuilders.cognitivemoodlog.BaseApplication.REQUEST_AUTHORIZATION;
//
//public class PreferenceActivity extends AppCompatActivity implements SettingsFragment.LoadingIndicatorCallback, EasyPermissions.PermissionCallbacks  {
//    ProgressBar loadingindicator;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_preference);
//        loadingindicator = (ProgressBar) findViewById(R.id.loading_indicator);
//        if (loadingindicator != null) {
//            loadingindicator.setVisibility(View.INVISIBLE);
//        }
////
////        Toolbar toolbar = (Toolbar) findViewById(R.id.preferences_toolbar);
////
////        setSupportActionBar(toolbar);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.preference_container, new SettingsFragment(this))
//                .commit();
//
//        // Show the Up button in the action bar.
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            // This ID represents the Home or Up button. In the case of this
//            // activity, the Up button is shown. For
//            // more details, see the Navigation pattern on Android Design:
//            //
//            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
//            //
//            navigateUpTo(new Intent(this, MainActivity.class));
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void showLoading() {
//        if (loadingindicator != null) {
//            loadingindicator.setVisibility(View.VISIBLE);
//
//
//        }
//    }
//
//    @Override
//    public void hideLoading() {
//        if (loadingindicator != null) {
//            loadingindicator.setVisibility(View.INVISIBLE);
//        }
//    }
//
//
//    @Override
//    public void onActivityResult(
//            int requestCode, int resultCode, Intent data) {
//        String op = "logon";
//        if (data.hasExtra("op")) {
//            op = data.getStringExtra("op");
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//        switch(requestCode) {
//            case BaseApplication.REQUEST_GOOGLE_PLAY_SERVICES:
//                if (resultCode != RESULT_OK) {
//                    Toast.makeText(this,  "This app requires Google Play Services. Please install " +
//                            "Google Play Services on your device and relaunch this app.", Toast.LENGTH_SHORT).show();
//                } else {
//                 //   BaseApplication.getResultsFromApi(new WhatToDoTask(this,op, null));
//                    if (BaseApplication.getLatch().getCount() > 0 ) {
//                        BaseApplication.getLatch().countDown();
//                    }
//                }
//                break;
//            case BaseApplication.REQUEST_ACCOUNT_PICKER:
//                if (resultCode == RESULT_OK && data != null &&
//                        data.getExtras() != null) {
//                    String accountName =
//                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
//                    if (accountName != null) {
//                        SharedPreferences settings =
//                                this.getPreferences(Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = settings.edit();
//                        editor.putString(BaseApplication.PREF_ACCOUNT_NAME, accountName);
//                        editor.apply();
//                        BaseApplication.getDriveCredential().setSelectedAccountName(accountName);
//                        if (BaseApplication.getLatch().getCount() > 0 ) {
//                            BaseApplication.getLatch().countDown();
//                        }
//                        //BaseApplication.getResultsFromApi(new WhatToDoTask(this, op, null) );
//                    }
//                }
//                break;
//            case REQUEST_AUTHORIZATION:
//                if (resultCode == RESULT_OK) {
//                  //  BaseApplication.getResultsFromApi(new WhatToDoTask(this,op, null));
//                    if (BaseApplication.getLatch().getCount() > 0 ) {
//                        BaseApplication.getLatch().countDown();
//                    }
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        if (BaseApplication.getLatch().getCount() > 0 ) {
//            BaseApplication.getLatch().countDown();
//        }
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        if (BaseApplication.getLatch().getCount() > 0 ) {
//            BaseApplication.getLatch().countDown();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (BaseApplication.getLatch().getCount() > 0 ) {
//            BaseApplication.getLatch().countDown();
//        }
//    }
//
//}
