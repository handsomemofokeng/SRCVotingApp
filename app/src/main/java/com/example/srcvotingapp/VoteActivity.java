package com.example.srcvotingapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.srcvotingapp.ui.main.SectionsPagerAdapter;

public class VoteActivity extends AppCompatActivity {

    public static String[] Portfolios = {"President", "Deputy President", "Secretary General",
            "Financial Officer", "Constitutional And Legal Affairs", "Sports Officer",
            "Public Relations Officer", "Health and Welfare Officer", "P C O", "Student Affairs",
            "E D O", "Transformation Officer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO: 2019/09/03 Testing
                                Toast.makeText(VoteActivity.this, "Action Clicked!!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
    }
}