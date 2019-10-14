package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.srcvotingapp.BL.Vote;
import com.example.srcvotingapp.ui.vote.SectionsPagerAdapter;
import com.example.srcvotingapp.ui.vote.VoteFragment;

import static com.example.srcvotingapp.ApplicationClass.buildAlertDialog;
import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.navigateTabs;
import static com.example.srcvotingapp.ApplicationClass.reverseTimer;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showProgressDialog;
import static com.example.srcvotingapp.ApplicationClass.showViews;

public class VoteActivity extends AppCompatActivity implements VoteFragment.SetCandidateListener {

    private ViewPager viewPager;
    private View toastView;
     TabLayout tabs;
    private Button btnNext, btnPrevious;
    private TextView tvTimer;
    private ProgressBar pbVotes;

    FloatingActionButton fabSubmitVotes;
    private Button btnSubmitVotes;

    int numVotesSoFar = 0;

//    PieChart pieChart;

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

        hideViews(btnSubmitVotes);//fabSubmitVotes);

//        pieChart.setData(setPieData("Votes"));
//        pieChart.setRotationEnabled(false);
//        pieChart.animateY(1000, Easing.EasingOption.EaseInOutBounce);


    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));

//        fabSubmitVotes = findViewById(R.id.fabSubmitVotes);
        btnSubmitVotes = findViewById(R.id.btnSubmitVotes);

        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tvTimer = findViewById(R.id.tvTimer);
        btnNext = findViewById(R.id.btnNavigateNextVote);
        btnPrevious = findViewById(R.id.btnNavigatePreviousVote);
        pbVotes = findViewById(R.id.pbVotes);

//        pieChart = findViewById(R.id.pieReview);

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

    @Override
    public void onSetCandidate(String candidatePartyID, String portfolio) {
        if (studentVote.getCandidateByPortfolio(portfolio)
                .equalsIgnoreCase("Not selected")) {
            studentVote.assignVotes(portfolio, candidatePartyID);
            if (!studentVote.getCandidateByPortfolio(portfolio)
                    .equalsIgnoreCase("Not selected")) {
                numVotesSoFar++;
                if (studentVote.isVotesValid()){
                    showViews(btnSubmitVotes);//fabSubmitVotes);
                    showCustomToast(getApplicationContext(), toastView, "Votes complete!");
                }
            }
        } else {
            studentVote.assignVotes(portfolio, candidatePartyID);
        }

        pbVotes.setProgress(numVotesSoFar);
//        showCustomToast(getApplicationContext(), toastView, studentVote.getCandidateByPortfolio(portfolio));
    }

//    public void onClick_SubmitVote(View view) {
//
//    }
//
//    public void onClick_EditVote(View view) {
//    }
//
//    public void onClick_NavigateSpinner(View view) {
////        navigateSpinner();
//    }

    public void onClick_NavigateTabs(View view) {
        navigateTabs(btnNext, btnPrevious, viewPager);
    }

    public void onClick_SubmitVotes(View view) {
        AlertDialog.Builder builder = buildAlertDialog(this, "Submit Votes",
                "Are you happy with your current selections?");

        builder.setPositiveButton("Yes, Vote!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // TODO: 2019/10/10 Register Votes to Backendless and Set hasVoted for User to True
                showProgressDialog(VoteActivity.this, "Submitting Votes",
                        "Please wait while we submit your selections...", true);

            }
        });

        builder.setNegativeButton("No, Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }
}