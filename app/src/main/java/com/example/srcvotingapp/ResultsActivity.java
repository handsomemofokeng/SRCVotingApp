package com.example.srcvotingapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.navigateSpinner;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;

public class ResultsActivity extends AppCompatActivity {

    private Typeface tfRegular, tfLight;

    //UI references
    View toastView;

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


        spnPortfolio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spnPortfolio.getSelectedItemPosition() > 0)
                    drawChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));

//        btnNext = findViewById(R.id.btnNavigateNextPortfolioResults);
//        btnPrevious = findViewById(R.id.btnNavigatePreviousPortfolioResults);

        spnPortfolio = findViewById(R.id.spnPortfolioResults);

    }

    private void drawChart() {
        BarChart chart = findViewById(R.id.barChart);
        BarDataSet set1, set2, set3;

        float groupSpace = 0.00f;
        float barSpace = 0.10f; // x4 DataSet
        float barWidth = 0.90f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
        //(barwidth + barspace) * no of bars + groupspace = 1

//        int groupCount = seekBarX.getProgress() + 1;
        int startYear = 1;
        int endYear = 3;

//        tvX.setText(String.format(Locale.ENGLISH, "%d-%d", startYear, endYear));
//        tvY.setText(String.valueOf(seekBarY.getProgress()));

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();

//        ArrayList<BarEntry> values4 = new ArrayList<>();

        float randomMultiplier = 1 * 100f;

        for (int i = startYear; i < endYear; i++) {
            values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values3.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));

//            values4.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
        }

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) chart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) chart.getData().getDataSetByIndex(2);

//            set4 = (BarDataSet) chart.getData().getDataSetByIndex(3);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);

//            set4.setValues(values4);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "DASO");
            set1.setColor(Color.BLUE);
            set2 = new BarDataSet(values2, "EFFSC");
            set2.setColor(Color.RED);
            set3 = new BarDataSet(values3, "SASCO");
            set3.setColor(Color.YELLOW);

//            set4 = new BarDataSet(values4, "Company D");
//            set4.setColor(Color.rgb(255, 102, 0));

            BarData data = new BarData(set1, set2, set3);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(tfLight);

            chart.setData(data);
        }

        // specify the width each bar should have
        chart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        chart.getXAxis().setAxisMinimum(startYear);

        for (IBarDataSet set : chart.getData().getDataSets()) {
            ((BarDataSet) set).setBarBorderWidth(1.f);
            set.setDrawValues(false);
        }

        chart.animateY(1000);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        chart.getXAxis().setAxisMaximum(startYear + chart.getBarData().getGroupWidth(groupSpace, barSpace) * 1.00f);
        chart.groupBars(startYear, groupSpace, barSpace);
        chart.invalidate();

    }
}
