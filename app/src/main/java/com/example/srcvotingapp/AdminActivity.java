package com.example.srcvotingapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.example.srcvotingapp.ApplicationClass.setupActionBar;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), getResources().getString(R.string.app_name),
                    "Admin Menu");
    }

    public void onClick_ViewResults(View view) {

    }

    public void onClick_AddCandidate(View view) {

        startActivity(new Intent(getApplicationContext(), AddCandidateActivity.class));

    }
}
