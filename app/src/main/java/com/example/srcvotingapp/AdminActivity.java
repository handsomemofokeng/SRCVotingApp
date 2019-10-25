package com.example.srcvotingapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.example.srcvotingapp.ApplicationClass.getUserFullName;
import static com.example.srcvotingapp.ApplicationClass.getUserString;
import static com.example.srcvotingapp.ApplicationClass.sessionUser;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), "Admin Menu",
                    getUserFullName(sessionUser));
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
