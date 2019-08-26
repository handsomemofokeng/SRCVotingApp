package com.example.srcvotingapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;

public class RegisterActivity extends AppCompatActivity {

    // UI references.
    private EditText etEmail;
    private EditText etPassword, etConfirm;
    private View toastView;
    private TextView tvCourse, tvRace;
    private Spinner spnEthnicity, spnCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), getResources().getString(R.string.app_name),
                    "Register User");

    }

    private void initViews() {
        toastView = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));
        etEmail = findViewById(R.id.etEmailReg);
        etPassword = findViewById(R.id.etPasswordReg);
        etConfirm = findViewById(R.id.etConfirmPassword);
        spnCourse = findViewById(R.id.spnCourse);
        spnEthnicity = findViewById(R.id.spnEthnicity);
        tvCourse = findViewById(R.id.tvCourse);
    }

    public void onClick_ScanCard(View view) {
        scanStudentCard();
    }


    private void scanStudentCard() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(Portrait.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan your student card...");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                showCustomToast(getApplicationContext(), toastView, "Result not found");
                etEmail.hasFocus();
            } else {
                etEmail.setText(String.format("%s@stud.cut.ac.za", result.getContents()));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onClick_RegisterUser(View view) {


    }
}
