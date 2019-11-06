package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.srcvotingapp.BL.Vote;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.srcvotingapp.ApplicationClass.HAS_VOTED;
import static com.example.srcvotingapp.ApplicationClass.Portfolios;

import static com.example.srcvotingapp.ApplicationClass.SELECTED_ConstitutionalAndLegalAffairs;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_DeputyPresident;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_EquityAndDiversityOfficer;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_FinancialOfficer;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_HealthAndWelfareOfficer;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_President;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_ProjectsAndCampaignOfficer;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_PublicRelationsOfficer;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_SecretaryGeneral;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_SportsOfficer;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_StudentAffairs;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_TransformationOfficer;
import static com.example.srcvotingapp.ApplicationClass.buildAlertDialog;
import static com.example.srcvotingapp.ApplicationClass.getUserFullName;
import static com.example.srcvotingapp.ApplicationClass.progressDialog;
import static com.example.srcvotingapp.ApplicationClass.selectAllQuery;
import static com.example.srcvotingapp.ApplicationClass.sessionUser;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showProgressDialog;
import static com.example.srcvotingapp.ApplicationClass.switchViews;


public class ResultsActivity extends AppCompatActivity {

    //UI references
    View toastView;
    TextView tvNumVotes;

    BarChart chart;
    List<Vote> currentVotes;

    //    Button btnNext, btnPrevious;
    //    Spinner spnPortfolio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), "Vote Results", getUserFullName(sessionUser));

        initViews();

        currentVotes = new ArrayList<>();

        getCurrentVotes();


//        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                showCustomToast(getApplicationContext(), toastView, e.getX() +"");
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });

//        spnPortfolio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                drawGroupChart();
////                if (spnPortfolio.getSelectedItemPosition() > 0)
////                    drawGroupChart();
////                    setData(3,100);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));
        chart = findViewById(R.id.barChart);
        tvNumVotes = findViewById(R.id.tvNumVotes);
    }

    private void drawGroupChart(List<Vote> voteList) {

        if (!voteList.isEmpty()) {
            ArrayList<BarEntry> entriesDASO = new ArrayList<>();
            ArrayList<BarEntry> entriesEFFSC = new ArrayList<>();
            ArrayList<BarEntry> entriesSASCO = new ArrayList<>();

            for (int i = 1; i <= 12; i++) {
                entriesDASO.add(new BarEntry(i, calcNumVotes("DASO", i, voteList)));
                entriesEFFSC.add(new BarEntry(i, calcNumVotes("EFFSC", i, voteList)));
                entriesSASCO.add(new BarEntry(i, calcNumVotes("SASCO", i, voteList)));
            }

            BarDataSet setDASO = new BarDataSet(entriesDASO, "DASO");
            setDASO.setColor(Color.BLUE);
            BarDataSet setEFFSC = new BarDataSet(entriesEFFSC, "EFFSC");
            setEFFSC.setColor(Color.RED);
            BarDataSet setSASCO = new BarDataSet(entriesSASCO, "SASCO");
            setSASCO.setColor(Color.YELLOW);

            BarData barData = new BarData(setDASO, setEFFSC, setSASCO);
            chart.setData(barData);

            XAxis xAxis = chart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(Portfolios));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);

            xAxis.setCenterAxisLabels(true);
            xAxis.setDrawGridLines(true);
            xAxis.setTextColor(Color.BLACK);
            xAxis.setTextSize(12);
            xAxis.setAxisLineColor(Color.WHITE);
            xAxis.setAxisMinimum(1f);

            chart.setDragEnabled(true);

            Configuration configuration = this.getResources().getConfiguration();
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                chart.setVisibleXRangeMaximum(1);
            } else {
                chart.setVisibleXRangeMaximum(2);
            }

            chart.getDescription().setPosition(0f, 1000f);

            float barSpace = 0.02f, groupSpace = 0.1f, barWidth = 0.28f;
            int numBars = 12;
            barData.setBarWidth(barWidth);

            chart.getXAxis().setAxisMinimum(0);

            chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace,
                    barWidth) * numBars);
            chart.getAxisLeft().setAxisMinimum(0);

            chart.groupBars(0, groupSpace, barSpace);

            chart.animateY(2000);
            chart.invalidate();
        } else {
            showMessageDialog("No Data", "Votes are empty, no data to display yet.");
        }

    }

    private int calcNumVotes(String partyID, int portfolioPosition, List<Vote> voteList) {

        tvNumVotes.setText(MessageFormat.format("Vote/s so far: {0}", currentVotes.size()));
        int voteCount = 0;

        if (!voteList.isEmpty()) {

            for (Vote vote : voteList) {
                switch (portfolioPosition){
                    case 1:
                        if (vote.getSelectedPresident().contains(partyID)) {
                            voteCount++;
                        }
                        break;
                    case 2:
                        if (vote.getSelectedDeputyPresident().contains(partyID)) {
                            voteCount++;
                        }
                        break;
                    case 3:
                        if (vote.getSelectedSecretaryGeneral().contains(partyID)) {
                            voteCount++;
                        }
                        break;
                    case 4:
                        if (vote.getSelectedFinancialOfficer().contains(partyID)) {
                            voteCount++;
                        }
                        break;
                    case 5:
                        if (vote.getSelectedConstitutionalAndLegalAffairs().contains(partyID)) {
                            voteCount++;
                        }
                        break;
                    case 6:
                        if (vote.getSelectedSportsOfficer().contains(partyID)) {
                            voteCount++;
                        }
                        break;
                    case 7:
                        if (vote.getSelectedPublicRelationsOfficer().contains(partyID)) {
                            voteCount++;
                        }
                        break;
                    case 8:
                        if (vote.getSelectedHealthAndWelfareOfficer().contains(partyID)) {
                            voteCount++;
                        }
                        break;
                    case 9:
                        if (vote.getSelectedProjectsAndCampaignOfficer().contains(partyID)) {
                            voteCount++;
                        }
                        break;
                    case 10:
                        if (vote.getSelectedStudentAffairs().contains(partyID)) {
                            voteCount++;
                        }
                        break;
                    case 11:
                        if (vote.getSelectedEquityAndDiversityOfficer().contains(partyID)) {
                            voteCount++;
                        }
                        break;
                    case 12:
                        if (vote.getSelectedTransformationOfficer().contains(partyID)) {
                            voteCount++;
                        }
                        break;
                }
            }

        } else {
            showCustomToast(ResultsActivity.this, toastView, "No votes yet.");
        }

        return voteCount;
    }

    private void getCurrentVotes() {

        showProgressDialog(ResultsActivity.this, "Retrieving Votes",
                "Please wait while we get votes...", false);
        Backendless.Data.of(Vote.class).find(selectAllQuery("created"),
                new AsyncCallback<List<Vote>>() {
                    @Override
                    public void handleResponse(List<Vote> response) {

                        progressDialog.dismiss();

                        //Add only Valid Votes
                        currentVotes.clear();
                        for (Vote vote : response) {
                            if (vote.isVotesValid())
                                currentVotes.add(vote);
                        }

                        if (!currentVotes.isEmpty())
                            drawGroupChart(response);
                        else
                            showMessageDialog("Votes Empty", "No votes yet...");

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        progressDialog.dismiss();
                        showMessageDialog("Error getting Votes", fault.getMessage());
                    }
                });

//                .find(selectAllQuery("created"),
//                new AsyncCallback<List<Map>>() {
//                    @Override
//                    public void handleResponse(List<Map> response) {
//                        progressDialog.dismiss();
//
//                        currentVotes.clear();
//
//                        Backendless.Data.mapTableToClass("Vote", Vote.class);
//
//                        for (Map vMap : response) {
//
//                            currentVotes.add(new Vote(vMap.get(SELECTED_President).toString(),
//                                    vMap.get(SELECTED_DeputyPresident).toString(),
//                                    vMap.get(SELECTED_SecretaryGeneral).toString(),
//                                    vMap.get(SELECTED_FinancialOfficer).toString(),
//                                    vMap.get(SELECTED_ConstitutionalAndLegalAffairs).toString(),
//                                    vMap.get(SELECTED_SportsOfficer).toString(),
//                                    vMap.get(SELECTED_PublicRelationsOfficer).toString(),
//                                    vMap.get(SELECTED_HealthAndWelfareOfficer).toString(),
//                                    vMap.get(SELECTED_ProjectsAndCampaignOfficer).toString(),
//                                    vMap.get(SELECTED_StudentAffairs).toString(),
//                                    vMap.get(SELECTED_EquityAndDiversityOfficer).toString(),
//                                    vMap.get(SELECTED_TransformationOfficer).toString()
//                            ));
//                        }
//                        if (!currentVotes.isEmpty())
//                            drawGroupChart(currentVotes);
//                        else
//                            showMessageDialog("Votes Empty", "No votes yet...");
//
//
//                    }
//
//                    @Override
//                    public void handleFault(BackendlessFault fault) {
//                        progressDialog.dismiss();
//                        showMessageDialog("Error", fault.getMessage());
//                    }
//                });

    }

    public void onClick_RefreshVotes(View view) {
        getCurrentVotes();
    }

    public void onClick_GoHome(View view) {
        finish();
    }

    private void showMessageDialog(String title, String message) {
        AlertDialog.Builder builder = buildAlertDialog(
                ResultsActivity.this, title, message);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }
}
