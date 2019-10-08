package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.srcvotingapp.BL.Vote;
import com.example.srcvotingapp.ui.vote.SectionsPagerAdapter;
import com.example.srcvotingapp.ui.vote.VoteFragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;

import java.sql.Time;
import java.util.Timer;

import static com.example.srcvotingapp.ApplicationClass.buildAlertDialog;
import static com.example.srcvotingapp.ApplicationClass.navigateTabs;
import static com.example.srcvotingapp.ApplicationClass.reverseTimer;
import static com.example.srcvotingapp.ApplicationClass.setPieData;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;

public class VoteActivity extends AppCompatActivity implements VoteFragment.SetCandidateListener {

    ViewPager viewPager;
    View toastView;
    TabLayout tabs;
    Button btnNext, btnPrevious;
    TextView tvTimer;

    PieChart pieChart;

    Vote studentVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        studentVote = new Vote();

        initViews();
//      28800s = 8h
        reverseTimer(600, tvTimer);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);

        navigateTabs(btnNext, btnPrevious, viewPager);

        pieChart.setData(setPieData("Votes"));
        pieChart.setRotationEnabled(false);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutBounce);


    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));

        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tvTimer = findViewById(R.id.tvTimer);
        btnNext = findViewById(R.id.btnNavigateNextVote);
        btnPrevious = findViewById(R.id.btnNavigatePreviousVote);

        pieChart = findViewById(R.id.pieReview);

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = buildAlertDialog(this, "Discard Changes",
                "Are you sure you want to exit without saving?" +
                        "\nAll progress will be lost!");

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

        navigateTabs(btnNext, btnPrevious, viewPager);
//        switch (view.getId()) {
//            case R.id.btnNavigateNextVote:
//
//                // TODO: 2019/09/03 Navigate next
//
//                if (viewPager.getCurrentItem() < (viewPager.getAdapter().getCount() - 1)) {
//                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
//                } else {
//                }
//                break;
//
//            case R.id.btnNavigatePreviousVote:
//
//                // TODO: 2019/09/03 Go back
//                if (viewPager.getCurrentItem() > 0) {
//                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
//                } else {
//                }
//                break;
//
//        }
    }

    @Override
    public void onSetCandidate(String candidatePartyID, String portfolio) {
        studentVote.assignVotes(portfolio, candidatePartyID);
        showCustomToast(getApplicationContext(), toastView, studentVote.toString());
    }

    public void onClick_SubmitVote(View view) {

    }

    public void onClick_EditVote(View view) {
    }
}