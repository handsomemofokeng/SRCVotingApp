package com.example.srcvotingapp;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import static com.example.srcvotingapp.ApplicationClass.Portfolios;

import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;

public class ResultsActivity extends AppCompatActivity
{

    private Typeface tfRegular, tfLight;

    //UI references
    View toastView;
    BarChart chart;

    //    Button btnNext, btnPrevious;
    Spinner spnPortfolio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), getResources().getString(R.string.app_name),
                    "Live Results");

        initViews();

        tfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        drawGroupChart();

//        // TODO: 2019/09/30 Show Candidate Name and Current Votes
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

        chart.animateXY(1500, 2000);
        chart.invalidate();

    }

    public void onClick_RefreshVotes(View view) {
        drawGroupChart();
    }

    public void onClick_GoBack(View view) {
        finish();
    }

//    private void drawChart() {
//
//         BarDataSet set1, set2, set3;
//
//        float groupSpace = 0.00f;
//        float barSpace = 0.10f; // x4 DataSet
//        float barWidth = 0.90f; // x4 DataSet
//        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
//        //(barwidth + barspace) * no of bars + groupspace = 1
//
////        int groupCount = seekBarX.getProgress() + 1;
//        int startYear = 1;
//        int endYear = 3;
//
////        tvX.setText(String.format(Locale.ENGLISH, "%d-%d", startYear, endYear));
////        tvY.setText(String.valueOf(seekBarY.getProgress()));
//
//        ArrayList<BarEntry> values1 = new ArrayList<>();
//        ArrayList<BarEntry> values2 = new ArrayList<>();
//        ArrayList<BarEntry> values3 = new ArrayList<>();
//
////        ArrayList<BarEntry> values4 = new ArrayList<>();
//
//        float randomMultiplier = 1 * 1000f;
//
//        for (int i = startYear; i < endYear; i++) {
//            values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
//            values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
//            values3.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
//
////            values4.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
//        }
//
//        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
//
//            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
//            set2 = (BarDataSet) chart.getData().getDataSetByIndex(1);
//            set3 = (BarDataSet) chart.getData().getDataSetByIndex(2);
//
////            set4 = (BarDataSet) chart.getData().getDataSetByIndex(3);
//            set1.setValues(values1);
//            set2.setValues(values2);
//            set3.setValues(values3);
//
////            set4.setValues(values4);
//            chart.getData().notifyDataChanged();
//            chart.notifyDataSetChanged();
//
//        } else {
//            // create 3 DataSets
//            set1 = new BarDataSet(values1, "DASO");
//            set2 = new BarDataSet(values2, "EFFSC");
//            set3 = new BarDataSet(values3, "SASCO");
//
//            set3.setColor(Color.YELLOW);
//            set1.setColor(Color.BLUE);
//            set2.setColor(Color.RED);
//
//
//            BarData data = new BarData(set1, set2, set3);
//            data.setValueFormatter(new LargeValueFormatter());
//            data.setValueTypeface(tfLight);
//
//            chart.setData(data);
//        }
//
//        // specify the width each bar should have
//        chart.getBarData().setBarWidth(barWidth);
//
//        // restrict the x-axis range
//        chart.getXAxis().setAxisMinimum(startYear);
//
//        for (IBarDataSet set : chart.getData().getDataSets()) {
//            ((BarDataSet) set).setBarBorderWidth(1.f);
//            set.setDrawValues(false);
//        }
//
//        chart.setPinchZoom(false);
////        chart.setHighlightFullBarEnabled(false);
//
//        chart.animateXY(1500, 1000);
//        chart.setDrawGridBackground(false);
//
//        Description description = new Description();
//        description.setText(getSpinnerValue(spnPortfolio));
//        description.setTextSize(8f);
//        description.setTextColor(R.color.colorPrimary);
//        chart.setDescription(description);
//
//        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
//        chart.getXAxis().setAxisMaximum(startYear + chart.getBarData().getGroupWidth(groupSpace, barSpace) * 1.00f);
//        chart.groupBars(startYear, groupSpace, barSpace);
//
//        chart.invalidate();
//
//    }

}
