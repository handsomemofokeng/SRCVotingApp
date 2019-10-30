package com.example.srcvotingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.srcvotingapp.BL.Vote;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

import static com.example.srcvotingapp.ApplicationClass.Portfolios;

import static com.example.srcvotingapp.ApplicationClass.ROLE;
import static com.example.srcvotingapp.ApplicationClass.getUserFullName;
import static com.example.srcvotingapp.ApplicationClass.sessionUser;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;

public class ResultsActivity extends AppCompatActivity {

    //UI references
    View toastView;
    BarChart chart;

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

        drawGroupChart();

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

//        btnNext = findViewById(R.id.btnNavigateNextPortfolioResults);
//        btnPrevious = findViewById(R.id.btnNavigatePreviousPortfolioResults);

        chart = findViewById(R.id.barChart);

//        spnPortfolio = findViewById(R.id.spnPortfolioResults);

    }

    // TODO: 2019/09/30 Get Votes from Backendless and pass them as Parameters
    private void drawGroupChart() {

        ArrayList<BarEntry> entriesDASO = new ArrayList<>();
        ArrayList<BarEntry> entriesEFFSC = new ArrayList<>();
        ArrayList<BarEntry> entriesSASCO = new ArrayList<>();

        // TODO: 2019/09/30 Comment out when connected to Backendless
        //Generate Random Entries
        for (int i = 1; i <= 12; i++) {
            entriesDASO.add(new BarEntry(i, (float) Math.random() * 1000f));
            entriesEFFSC.add(new BarEntry(i, (float) Math.random() * 1000f));
            entriesSASCO.add(new BarEntry(i, (float) Math.random() * 1000f));
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

        chart.animateXY(4000, 2000);
        chart.invalidate();

    }

    private void countVotes(Vote... votes) {

    }

    public void onClick_GoBack(View view) {
        finish();
    }

    public void onClick_GoHome(View view) {
        finish();
    }
}
