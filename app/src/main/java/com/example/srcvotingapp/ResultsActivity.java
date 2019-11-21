package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.srcvotingapp.BL.Vote;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.srcvotingapp.ApplicationClass.Portfolios;
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

    BarChart barChart;
    PieChart pieChart;

    List<Vote> currentVotes;

    boolean isChart = true;

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

        setPieData(pieChart);
//        pieChart.setData(setPieData("Overall Votes", 22));
//        pieChart.setRotationEnabled(false);
//        pieChart.animateY(1000, Easing.EasingOption.EaseInOutBounce);

//        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
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
        barChart = findViewById(R.id.barChart);
        pieChart = findViewById(R.id.pieChart);
        tvNumVotes = findViewById(R.id.tvNumVotes);
    }

    private void drawGroupChart(List<Vote> voteList) {

        if (!voteList.isEmpty()) {
            ArrayList<BarEntry> entriesDASO = new ArrayList<>();
            ArrayList<BarEntry> entriesEFFSC = new ArrayList<>();
            ArrayList<BarEntry> entriesSASCO = new ArrayList<>();

            for (int i = 1; i <= 12; i++) {
                entriesDASO.add(new BarEntry(i, calcVotesPerPortfolio("DASO", i, voteList)));
                entriesEFFSC.add(new BarEntry(i, calcVotesPerPortfolio("EFFSC", i, voteList)));
                entriesSASCO.add(new BarEntry(i, calcVotesPerPortfolio("SASCO", i, voteList)));
            }

            BarDataSet setDASO = new BarDataSet(entriesDASO, "DASO");
            setDASO.setColor(ColorTemplate.MATERIAL_COLORS[3]);
            BarDataSet setEFFSC = new BarDataSet(entriesEFFSC, "EFFSC");
            setEFFSC.setColor(ColorTemplate.MATERIAL_COLORS[2]);
            BarDataSet setSASCO = new BarDataSet(entriesSASCO, "SASCO");
            setSASCO.setColor(ColorTemplate.MATERIAL_COLORS[1]);

            BarData barData = new BarData(setDASO, setEFFSC, setSASCO);
            barChart.setData(barData);

            XAxis xAxis = barChart.getXAxis();
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

            barChart.setDragEnabled(true);

            Configuration configuration = this.getResources().getConfiguration();
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                barChart.setVisibleXRangeMaximum(1);
            } else {
                barChart.setVisibleXRangeMaximum(2);
            }

            barChart.getDescription().setPosition(0f, 1000f);

            float barSpace = 0.02f, groupSpace = 0.1f, barWidth = 0.28f;
            int numBars = 12;
            barData.setBarWidth(barWidth);

            barChart.getXAxis().setAxisMinimum(0);

            barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace,
                    barWidth) * numBars);
            barChart.getAxisLeft().setAxisMinimum(0);

            barChart.groupBars(0, groupSpace, barSpace);

            barChart.animateY(2000);
            barChart.invalidate();
        } else {
            showMessageDialog("No Data", "Votes are empty, no data to display yet.");
        }

    }

    private int calcVotesPerPortfolio(String partyID, int portfolioPosition, List<Vote> voteList) {

        tvNumVotes.setText(MessageFormat.format("Vote/s so far: {0}", currentVotes.size()));

        int voteCount = 0;

        if (!voteList.isEmpty()) {

            for (Vote vote : voteList) {
                switch (portfolioPosition) {
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

    public static int calcOverrallVotes(String target, List<Vote> voteList) {

        int num = 0;

        for (Vote vote : voteList) {
            num += vote.toString().split(target, -1).length -1;
        }


        return num;
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

    public void onClick_SwitchGraphs(View view) {
        FloatingActionButton fabSwitchGraphs = (FloatingActionButton) view;
        if (isChart) {
            fabSwitchGraphs.setImageDrawable(getDrawable(R.drawable.ic_poll));
            isChart = false;
            switchViews(pieChart, barChart);
            pieChart.animateY(2000);

            setPieData(pieChart);
        } else {
            fabSwitchGraphs.setImageDrawable(getDrawable(R.drawable.ic_show_chart));
            isChart = true;
            switchViews(barChart, pieChart);
            barChart.animateY(2000);
        }
    }

    public void setPieData(PieChart chart) {

        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

//        chart.setCenterTextTypeface(tfLight);
//        chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
//        chart.setOnChartValueSelectedListener(this);


//        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE);
//        chart.setEntryLabelTypeface(tfRegular);
        chart.setEntryLabelTextSize(12f);

        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their
        // position around the center of the chart.
        String[] parties = {"DASO: "+ calcOverrallVotes("DASO", currentVotes),
                "SASCO: "+calcOverrallVotes("SASCO", currentVotes),
                "EFFSC: " +calcOverrallVotes("EFFSC", currentVotes)};
        for (int i = 0; i < 3; i++) {   //3 = range
            if (i == 0)//DASO
                entries.add(new PieEntry((calcOverrallVotes("DASO", currentVotes)) * 1f,
                        parties[i % parties.length],
                        getDrawable(R.drawable.ic_thumb_up)));
            if (i == 1)//SASCO
                entries.add(new PieEntry((calcOverrallVotes("SASCO", currentVotes)) * 1f,
                        parties[i % parties.length],
                        getDrawable(R.drawable.ic_thumb_up)));
            if (i == 2)//EFFSC
                entries.add(new PieEntry((calcOverrallVotes("EFFSC", currentVotes)) * 1f,
                        parties[i % parties.length],
                        getDrawable(R.drawable.ic_thumb_up)));

        }

        PieDataSet dataSet = new PieDataSet(entries, "Overall Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.MATERIAL_COLORS)
//            colors.add(c);

        //Order corresponds to each Party's primary color
        colors.add(ColorTemplate.MATERIAL_COLORS[3]);
        colors.add(ColorTemplate.MATERIAL_COLORS[1]);
        colors.add(ColorTemplate.MATERIAL_COLORS[2]);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(tfLight);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }


}
