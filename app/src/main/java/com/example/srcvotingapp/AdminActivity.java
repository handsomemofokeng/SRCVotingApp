package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import static com.example.srcvotingapp.ApplicationClass.buildAlertDialog;
import static com.example.srcvotingapp.ApplicationClass.getUserFullName;
import static com.example.srcvotingapp.ApplicationClass.progressDialog;
import static com.example.srcvotingapp.ApplicationClass.sessionUser;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showProgressDialog;

public class AdminActivity extends AppCompatActivity {

    View toastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), "Admin Menu",
                    getUserFullName(sessionUser));
        initViews();
    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));
    }

    @Override
    public void onBackPressed() {
        showSignOutDialog();
    }

    private void showSignOutDialog() {
        AlertDialog.Builder builder = buildAlertDialog(AdminActivity.this,
                "Sign Out", sessionUser.getEmail() + " is about to be signed out\n" +
                        "\nContinue signing out?");

        builder.setPositiveButton("Yes, Sign Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                StudentActivity.super.onBackPressed();
                logOutUser();
            }
        });

        builder.setNegativeButton("No, Stay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void logOutUser() {

        try {
            showProgressDialog(AdminActivity.this, "Signing Out",
                    "Please wait...", false);
            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void aVoid) {
                    progressDialog.dismiss();
                    showCustomToast(getApplicationContext(), toastView,
                            sessionUser.getEmail()
                                    + " signed out successfully.");

//                    commitMyPrefs("", "", false);
                    finish();
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                    progressDialog.dismiss();
                    showCustomToast(getApplicationContext(), toastView, "Error: "
                            + backendlessFault.getMessage());
                }
            });
        } catch (Exception ex) {
            showCustomToast(getApplicationContext(), toastView, "Error: " + ex.getMessage());
        }

        //return feedBack;
    }//end method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ab_sign_off:
                showSignOutDialog();
                break;
            default:
                showCustomToast(AdminActivity.this, toastView,
                        "Unexpected value: " + item.getItemId());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick_ViewResults(View view) {
        startActivity(new Intent(getApplicationContext(), ResultsActivity.class));
    }

    public void onClick_AddCandidate(View view) {

        startActivity(new Intent(getApplicationContext(), AddCandidateActivity.class));

    }

    public void onClick_StartElections(View view) {
        // TODO: 2019/09/25 send notifications to students and set the timer

    }

    public void onClick_ManageParties(View view) {
        startActivity(new Intent(AdminActivity.this, ManagePartiesActivity.class));
    }
}
