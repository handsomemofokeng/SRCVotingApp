package com.example.srcvotingapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.example.srcvotingapp.ApplicationClass.COURSE;
import static com.example.srcvotingapp.ApplicationClass.ETHNICITY;
import static com.example.srcvotingapp.ApplicationClass.GENDER;
import static com.example.srcvotingapp.ApplicationClass.HAS_VOTED;
import static com.example.srcvotingapp.ApplicationClass.IS_CANDIDATE;
import static com.example.srcvotingapp.ApplicationClass.NAME;
import static com.example.srcvotingapp.ApplicationClass.SURNAME;
import static com.example.srcvotingapp.ApplicationClass.clearFields;
import static com.example.srcvotingapp.ApplicationClass.clearRadioGroup;
import static com.example.srcvotingapp.ApplicationClass.clearSpinners;
import static com.example.srcvotingapp.ApplicationClass.getSelectedRadio;
import static com.example.srcvotingapp.ApplicationClass.getSpinnerValue;
import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.isPasswordsMatching;
import static com.example.srcvotingapp.ApplicationClass.isRadioChecked;
import static com.example.srcvotingapp.ApplicationClass.isValidFields;
import static com.example.srcvotingapp.ApplicationClass.isValidSpinner;
import static com.example.srcvotingapp.ApplicationClass.scanStudentCard;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showViews;

public class RegisterActivity extends AppCompatActivity {

    // UI references.
    private EditText etEmail, etName, etSurname, etPassword, etConfirm;
    private View toastView;
    private TextView tvCourse, tvEthnicity, tvGender;
    private Spinner spnEthnicity, spnCourse;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private Button btnNavigate, btnRegister;
    private LinearLayout frmPersonalDetails, frmStatisticalDetails;

    BackendlessUser newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO: 2019/08/28
                tvGender.setError(null);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), getResources().getString(R.string.app_name),
                    "Register User");

        etConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (isValidFields(etPassword, etConfirm) &&
                        isPasswordsMatching(etPassword, etConfirm)) {
                    etPassword.setError(null);
                    etConfirm.setError(null);
                }
            }
        });

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnNavigate.getText().toString().equals(getString(R.string.action_next))) {

                    if (isValidFields(etEmail, etName, etSurname, etPassword, etConfirm)
                            && isPasswordsMatching(etPassword, etConfirm)) {

                        newUser = new BackendlessUser();

                        newUser.setEmail(etEmail.getText().toString().trim());
                        newUser.setPassword(etConfirm.getText().toString().trim());

                        newUser.setProperty(NAME, etName.getText().toString().trim());
                        newUser.setProperty(SURNAME, etSurname.getText().toString().trim());
                        newUser.setProperty(GENDER, getSelectedRadio(rbFemale, rbMale));
                        newUser.setProperty(HAS_VOTED, false);
                        newUser.setProperty(IS_CANDIDATE, false);

                        showViews(frmStatisticalDetails, btnRegister);
                        hideViews(frmPersonalDetails);
                        btnNavigate.setText(R.string.action_go_back);

                    } else {

                        showCustomToast(getApplicationContext(), toastView,
                                "Please enter required fields");

                    }

                } else {

                    hideViews(frmStatisticalDetails, btnRegister);
                    showViews(frmPersonalDetails);
                    btnNavigate.setText(R.string.action_next);

                }
            }
        });

    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));
        etEmail = findViewById(R.id.etEmailReg);
        etName = findViewById(R.id.etNameReg);
        etSurname = findViewById(R.id.etSurnameReg);
        tvGender = findViewById(R.id.tvGenderReg);
        etPassword = findViewById(R.id.etPasswordReg);
        etConfirm = findViewById(R.id.etConfirmPassword);
        spnCourse = findViewById(R.id.spnCourseReg);
        spnEthnicity = findViewById(R.id.spnEthnicityReg);
        tvCourse = findViewById(R.id.tvCourse);
        tvEthnicity = findViewById(R.id.tvEthnicity);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnNavigate = findViewById(R.id.btnNavigateReg);
        btnRegister = findViewById(R.id.btnRegisterUser);
        frmPersonalDetails = findViewById(R.id.frmPersonalDetails);
        frmStatisticalDetails = findViewById(R.id.frmStatisticalDetails);

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

            } else {
                etEmail.setText(String.format("%s@stud.cut.ac.za", result.getContents()));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onClick_RegisterUser(View view) {

        if (isRadioChecked(rbFemale, rbMale) && isValidSpinner(spnCourse, spnEthnicity)) {

            tvGender.setError(null);
            tvEthnicity.setError(null);
            tvCourse.setError(null);

            newUser.setProperty(GENDER, getSelectedRadio(rbMale, rbFemale));
            newUser.setProperty(ETHNICITY, getSpinnerValue(spnEthnicity));
            newUser.setProperty(COURSE, getSpinnerValue(spnCourse));

//            if (isValidSpinner(spnCourse, spnEthnicity)) {
//                tvCourse.setError(null);
//                tvEthnicity.setError(null);
//                if (isPasswordsMatching(etPassword, etConfirm)) {
//
//                    // TODO: 2019/08/26 Implement Register Code
//
////                    Backendless.UserService.register(newUser, new AsyncCallback<BackendlessUser>() {
////                        @Override
////                        public void handleResponse(BackendlessUser response) {
////
////                        }
////
////                        @Override
////                        public void handleFault(BackendlessFault fault) {
////
////                        }
////                    });
//
//
//                    //Then reset form
//
//                    clearFields(etEmail, etName, etSurname, etPassword, etConfirm);
//                    clearRadioGroup(rgGender);
//                    clearSpinners(spnCourse, spnEthnicity);
//                    etEmail.hasFocus();
//
//                    showCustomToast(getApplicationContext(), toastView,
//                            "User successfully registered");
//
//                } else {
//
//                    showCustomToast(getApplicationContext(), toastView,
//                            "Please make sure passwords match");
//
//                }
//
//            } else {
//                if (!isValidSpinner(spnEthnicity, spnCourse))
//                } else {
//
//                }
//            }

        } else {

            if (!isRadioChecked(rbFemale, rbMale))
                tvGender.setError("Please specify gender");

            if (!isValidSpinner(spnCourse))
                tvCourse.setError("Please select Course on the dropdown list");

            if (!isValidSpinner(spnEthnicity))
                tvEthnicity.setError("Please select Ethnicity on the dropdown list");
        }

    }
}