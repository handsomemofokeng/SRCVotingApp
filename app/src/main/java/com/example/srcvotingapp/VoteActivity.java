package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.srcvotingapp.BL.Party;
import com.example.srcvotingapp.BL.Vote;
import com.example.srcvotingapp.ui.vote.SectionsPagerAdapter;
import com.example.srcvotingapp.ui.vote.VoteFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.srcvotingapp.ApplicationClass.HAS_VOTED;
import static com.example.srcvotingapp.ApplicationClass.buildAlertDialog;
import static com.example.srcvotingapp.ApplicationClass.getUserFullName;
import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.navigateTabs;
import static com.example.srcvotingapp.ApplicationClass.progressDialog;
import static com.example.srcvotingapp.ApplicationClass.reverseTimer;
import static com.example.srcvotingapp.ApplicationClass.selectAllQuery;
import static com.example.srcvotingapp.ApplicationClass.sessionUser;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showProgressDialog;
import static com.example.srcvotingapp.ApplicationClass.showViews;

public class VoteActivity extends AppCompatActivity implements VoteFragment.SetCandidateListener {

    private ViewPager viewPager;
    private View toastView;
    TabLayout tabs;
    private Button btnNext, btnPrevious;
    private TextView tvTimer, tvSubtitle;
    private ProgressBar pbVotes;

//    RadioButton rbDASO, rbEFFSC, rbSASCO;
//    String candidateDASO, candidateEFFSC, candidateSASCO;

    public static List<Party> partyList;

    //    FloatingActionButton fabSubmitVotes;
    private Button btnSubmitVotes;

    int numVotesSoFar = 0;

//    PieChart pieChart;

    Vote studentVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        studentVote = new Vote();

        studentVote.setSelectedPresident("Not selected");
        studentVote.setSelectedDeputyPresident("Not selected");
        studentVote.setSelectedSecretaryGeneral("Not selected");
        studentVote.setSelectedFinancialOfficer("Not selected");
        studentVote.setSelectedConstitutionalAndLegalAffairs("Not selected");
        studentVote.setSelectedSportsOfficer("Not selected");
        studentVote.setSelectedPublicRelationsOfficer("Not selected");
        studentVote.setSelectedHealthAndWelfareOfficer("Not selected");
        studentVote.setSelectedProjectsAndCampaignOfficer("Not selected");
        studentVote.setSelectedStudentAffairs("Not selected");
        studentVote.setSelectedEquityAndDiversityOfficer("Not selected");
        studentVote.setSelectedTransformationOfficer("Not selected");

        initViews();
//      28800s = 8h
        reverseTimer(600, tvTimer);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager());

        viewPager.setAdapter(sectionsPagerAdapter);

        tvSubtitle.setText(getUserFullName(sessionUser));

        navigateTabs(btnNext, btnPrevious, viewPager);

        hideViews(btnSubmitVotes);//fabSubmitVotes);

//        pieChart.setData(setPieData("Votes"));
//        pieChart.setRotationEnabled(false);
//        pieChart.animateY(1000, Easing.EasingOption.EaseInOutBounce);


    }

    @Override
    protected void onResume() {
        super.onResume();

        fetchParties();
    }

    private void fetchParties() {
        partyList = new ArrayList<>();
        showProgressDialog(VoteActivity.this, "Getting Parties",
                "Please wait while we load Parties....", false);
        Backendless.Data.of(Party.class).find(selectAllQuery("partyID"),
                new AsyncCallback<List<Party>>() {
                    @Override
                    public void handleResponse(List<Party> response) {
                        progressDialog.dismiss();

                        partyList.clear();
                        partyList.addAll(response);
                        viewPager.setCurrentItem(Objects.requireNonNull(viewPager.getAdapter()).getCount());
                        AlertDialog.Builder builder = buildAlertDialog(VoteActivity.this,
                                "Start Voting", "Candidates loaded successfully, start casting your votes!");
                        builder.setPositiveButton("Let's do this!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewPager.setCurrentItem(0, true);
                            }
                        }).create().show();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        progressDialog.dismiss();
                        showMessageDialog("Error loading Parties", fault.getMessage());
                    }

                });
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
        tvSubtitle = findViewById(R.id.tvSubtitle);
        btnNext = findViewById(R.id.btnNavigateNextVote);
        btnPrevious = findViewById(R.id.btnNavigatePreviousVote);
        pbVotes = findViewById(R.id.pbVotes);

//        pieChart = findViewById(R.id.pieReview);

//        rbDASO = findViewById(R.id.rbDASO);
//        rbEFFSC = findViewById(R.id.rbEFFSC);
//        rbSASCO = findViewById(R.id.rbSASCO);

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = buildAlertDialog(this, "Discard Changes",
                "Are you sure you want to exit without saving?" +
                        "\n\nAll progress will be lost!");

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
                if (studentVote.isVotesValid()) {
                    showViews(btnSubmitVotes);//fabSubmitVotes);
                    showCustomToast(getApplicationContext(), toastView, "Elections complete!");
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

        builder.setPositiveButton("Yes, Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                showProgressDialog(VoteActivity.this, "Submitting Votes",
                        "Please wait while we submit your selections...", false);
                Backendless.Data.of(Vote.class).save(studentVote, new AsyncCallback<Vote>() {
                    @Override
                    public void handleResponse(Vote response) {
                        sessionUser.setProperty(HAS_VOTED, true);
                        progressDialog.setMessage("Updating your vote status...");
                        Backendless.Data.of(BackendlessUser.class).save(sessionUser,
                                new AsyncCallback<BackendlessUser>() {
                                    @Override
                                    public void handleResponse(BackendlessUser response) {
                                        progressDialog.dismiss();
                                        showVoteConfirmedDialog();
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {
                                        progressDialog.dismiss();
                                        showMessageDialog("Vote Status Error", fault.getMessage());
                                    }
                                });
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        progressDialog.dismiss();
                        showMessageDialog("Voting Error", fault.getMessage());
                    }
                });

            }
        });

        builder.setNegativeButton("No, Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewPager.setCurrentItem(0, true);
            }
        });

        builder.create().show();
    }

    private void showVoteConfirmedDialog() {
        AlertDialog.Builder builder = buildAlertDialog(VoteActivity.this,
                "Vote Saved",
                "Your votes have been submitted successfully." +
                        "\n\nWhere to from here?");

        builder.setPositiveButton("View Results", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(VoteActivity.this,
                        ResultsActivity.class));

                finish();
            }
        });

        builder.setNegativeButton("View Profile",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.dismiss();
                        startActivity(new Intent(VoteActivity.this,
                                StudentActivity.class));

                        finish();
                    }
                });

        builder.create().show();
    }

    private void showMessageDialog(String title, String message) {
        AlertDialog.Builder builder = buildAlertDialog(
                VoteActivity.this, title, message);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

    }
}