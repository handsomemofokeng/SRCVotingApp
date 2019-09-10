package com.example.srcvotingapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.scanStudentCard;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showViews;

public class AddCandidateActivity extends AppCompatActivity {

    View toastView;
    EditText etEmail;
    ImageView ivScanCard, ivSearch, ivResetParty;
    RadioGroup rgCandidatePartyRegCan;
    RadioButton rbEFFSC, rbDASO, rbSASCO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_candidate);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), getResources().getString(R.string.app_name),
                    "Register New Candidate");

        initViews();

        rgCandidatePartyRegCan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO: 2019/09/10 Hide Unselected Radios
                hideViews(rbEFFSC, rbDASO, rbSASCO);
                showViews(ivResetParty, findViewById(checkedId));
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                showScanButton();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showScanButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains("@")) {
                    showSearchButton();
                } else {
                    showScanButton();
                }
            }
        });
    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout));
        etEmail = findViewById(R.id.etEmailRegCan);
        ivScanCard = findViewById(R.id.ivScanCardRegCan);
        ivSearch = findViewById(R.id.ivSearchRegCan);

        rgCandidatePartyRegCan = findViewById(R.id.rgCandidateRegCan);
        rbDASO = findViewById(R.id.rbDASORegCan);
        rbEFFSC = findViewById(R.id.rbEFFSCRegCan);
        rbSASCO = findViewById(R.id.rbSASCORegCan);

        ivResetParty = findViewById(R.id.ivResetParty);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                showCustomToast(getApplicationContext(), toastView, "Result not found");
                etEmail.findFocus();
            } else {

                etEmail.setText(String.format("%s@stud.cut.ac.za", result.getContents()));
                showSearchButton();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showScanButton() {
        showViews(ivScanCard);
        hideViews(ivSearch);
    }

    private void showSearchButton() {
        showViews(ivSearch);
        hideViews(ivScanCard);
    }

    public void onClick_ScanCard(View view) {

        scanStudentCard(this);
        showCustomToast(getApplicationContext(), toastView, "Scan Student Card");
    }

    public void onClick_SearchEmail(View view) {
        // TODO: 2019/09/10 Search by Email
    }

    public void onClick_ResetPartySelection(View view) {
        hideViews(view);
        showViews(rbEFFSC, rbDASO, rbSASCO);
    }

}
