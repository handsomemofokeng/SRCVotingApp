package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.srcvotingapp.ui.main.SectionsPagerAdapter;

import static com.example.srcvotingapp.ApplicationClass.Portfolios;
import static com.example.srcvotingapp.ApplicationClass.buildAlertDialog;

public class VoteActivity extends AppCompatActivity {
    int tabNo = 0;

    ViewPager viewPager;
    View toastView;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        initViews();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);


//        FloatingActionButton fab = findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                // TODO: 2019/09/03 Testing
//                                Toast.makeText(VoteActivity.this, "Action Clicked!!!",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }).show();
//            }
//        });

    }


    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));

        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = buildAlertDialog(this, "Discard Changes",
                "Are you sure you want to exit without saving?" +
                        "\nAll progress will be lost");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                VoteActivity.super.onBackPressed();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();

    }

    public void onClick_Navigate(View view) {

        switch (view.getId()) {

            case R.id.btnNavigateNextVote:

                // TODO: 2019/09/03 Navigate next
                if (tabNo <= Portfolios.length && tabNo >= 0) {

                    tabNo++;
                    viewPager.setCurrentItem(tabNo);
                }

                break;

            case R.id.btnNavigatePreviousVote:

                // TODO: 2019/09/03 Go back
                if (tabNo <= Portfolios.length && tabNo >= 0) {
                    tabNo--;
                    viewPager.setCurrentItem(tabNo);
                }
                break;

        }
    }
}