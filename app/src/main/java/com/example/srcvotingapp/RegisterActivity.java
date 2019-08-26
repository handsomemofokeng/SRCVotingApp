package com.example.srcvotingapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.example.srcvotingapp.ApplicationClass.clearFields;
import static com.example.srcvotingapp.ApplicationClass.clearRadioGroup;
import static com.example.srcvotingapp.ApplicationClass.clearSpinners;
import static com.example.srcvotingapp.ApplicationClass.getSelectedRadio;
import static com.example.srcvotingapp.ApplicationClass.isPasswordsMatching;
import static com.example.srcvotingapp.ApplicationClass.isValidFields;
import static com.example.srcvotingapp.ApplicationClass.isValidSpinner;
import static com.example.srcvotingapp.ApplicationClass.scanStudentCard;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;

public class RegisterActivity extends AppCompatActivity {

    // UI references.
    private EditText etEmail, etName, etSurname, etGender, etPassword, etConfirm;
    private View toastView;
    private TextView tvCourse, tvEthnicity;
    private Spinner spnEthnicity, spnCourse;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                etGender.setText(getSelectedRadio(rbMale, rbFemale));
                etGender.setError(null);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), getResources().getString(R.string.app_name),
                    "Register User");

    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));
        etEmail = findViewById(R.id.etEmailReg);
        etName = findViewById(R.id.etNameReg);
        etSurname = findViewById(R.id.etSurnameReg);
        etGender = findViewById(R.id.etGenderReg);
        etPassword = findViewById(R.id.etPasswordReg);
        etConfirm = findViewById(R.id.etConfirmPassword);
        spnCourse = findViewById(R.id.spnCourseReg);
        spnEthnicity = findViewById(R.id.spnEthnicityReg);
        tvCourse = findViewById(R.id.tvCourse);
        tvEthnicity = findViewById(R.id.tvEthnicity);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

    }

    public void onClick_ScanCard(View view) {
        scanStudentCard(this);
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

        if (isValidFields(etEmail, etName, etSurname, etGender, etPassword, etConfirm)) {

            if (isValidSpinner(spnCourse, spnEthnicity)) {
                tvCourse.setError(null);
                tvEthnicity.setError(null);
                if (isPasswordsMatching(etPassword, etConfirm)) {
                    //Register

                    //Then reset form
                    clearFields(etEmail, etName, etSurname, etGender, etPassword, etConfirm);
                    clearRadioGroup(rgGender);
                    clearSpinners(spnCourse,spnEthnicity);

                    showCustomToast(getApplicationContext(), toastView,
                            "User successfully registered");

                } else {

                    showCustomToast(getApplicationContext(), toastView,
                            "Please make sure passwords match");

                }
            } else {
                if (!isValidSpinner(spnEthnicity)) {
                    tvEthnicity.setError("Please select Ethnicity on the dropdown list");
                }
                if (!isValidSpinner(spnCourse)) {
                    tvCourse.setError("Please select Course on the dropdown list");
                }
            }

        } else {

            showCustomToast(getApplicationContext(), toastView,
                    "Please enter required fields");
        }

    }
}
