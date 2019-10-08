package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.backendless.BackendlessUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.example.srcvotingapp.ApplicationClass.COURSE;
import static com.example.srcvotingapp.ApplicationClass.ETHNICITY;
import static com.example.srcvotingapp.ApplicationClass.GENDER;
import static com.example.srcvotingapp.ApplicationClass.HAS_VOTED;
import static com.example.srcvotingapp.ApplicationClass.IS_CANDIDATE;
import static com.example.srcvotingapp.ApplicationClass.NAME;
import static com.example.srcvotingapp.ApplicationClass.ROLE;
import static com.example.srcvotingapp.ApplicationClass.SURNAME;
import static com.example.srcvotingapp.ApplicationClass.clearFields;
import static com.example.srcvotingapp.ApplicationClass.clearRadioGroup;
import static com.example.srcvotingapp.ApplicationClass.clearSpinners;
import static com.example.srcvotingapp.ApplicationClass.buildAlertDialog;
import static com.example.srcvotingapp.ApplicationClass.getSelectedRadio;
import static com.example.srcvotingapp.ApplicationClass.getSpinnerValue;
import static com.example.srcvotingapp.ApplicationClass.getUserString;
import static com.example.srcvotingapp.ApplicationClass.isEmailValid;
import static com.example.srcvotingapp.ApplicationClass.isPasswordsMatching;
import static com.example.srcvotingapp.ApplicationClass.isRadioChecked;
import static com.example.srcvotingapp.ApplicationClass.isValidFields;
import static com.example.srcvotingapp.ApplicationClass.isValidSpinner;
import static com.example.srcvotingapp.ApplicationClass.scanStudentCard;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.switchViews;
import static com.example.srcvotingapp.ApplicationClass.validateEmailInput;
import static com.example.srcvotingapp.ApplicationClass.validatePasswordInput;

public class RegisterActivity extends AppCompatActivity {

    // UI references.
    private EditText etEmail, etName, etSurname, etPassword, etConfirm;
    private View toastView;
    private TextView tvCourse, tvEthnicity, tvGender;
    private Spinner spnEthnicity, spnCourse;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private Button btnRegister;
    private ImageView ivScanCard, ivCorrect;
//    private LinearLayout frmPersonalDetails, frmStatisticalDetails;


    BackendlessUser newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), getResources().getString(R.string.app_name),
                    "Register User");

        tvCourse.setError("");
        tvEthnicity.setError("");
        tvGender.setError("");

        validateEmailInput(etEmail, ivScanCard, ivCorrect);
        validatePasswordInput(etPassword);

        validatePasswordInput(etConfirm);

        spnCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                    tvCourse.setError(null);
                else
                    tvCourse.setError("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnEthnicity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                    tvEthnicity.setError(null);
                else
                    tvEthnicity.setError("");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO: 2019/08/28
                tvGender.setError(null);
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
        btnRegister = findViewById(R.id.btnRegisterUser);
        ivCorrect = findViewById(R.id.ivCorrectRegUser);
        ivScanCard = findViewById(R.id.ivScanCardRegUser);

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
                etEmail.setError(null);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = buildAlertDialog(this, "Discard Changes",
                "Are you sure you want to exit without saving?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RegisterActivity.super.onBackPressed();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();

    }

    public void onClick_RegisterUser(View view) {

        if (isValidPersonalDetails()) {

            newUser = new BackendlessUser();

            newUser.setEmail(etEmail.getText().toString().trim());
            newUser.setPassword(etConfirm.getText().toString().trim());
            newUser.setProperty(NAME, etName.getText().toString().trim());
            newUser.setProperty(SURNAME, etSurname.getText().toString().trim());
            newUser.setProperty(GENDER, getSelectedRadio(rbFemale, rbMale));
            newUser.setProperty(HAS_VOTED, false);
            newUser.setProperty(IS_CANDIDATE, false);
            newUser.setProperty(ROLE, "Student");

            if (isValidStatDetails()) {

                newUser.setProperty(GENDER, getSelectedRadio(rbMale, rbFemale));
                newUser.setProperty(ETHNICITY, getSpinnerValue(spnEthnicity));
                newUser.setProperty(COURSE, getSpinnerValue(spnCourse));


                showCustomToast(getApplicationContext(), toastView, getUserString(newUser)
                        + " registered successfully.");

                AlertDialog.Builder builder = buildAlertDialog(this,
                        "Registration Co", "Student registered successfully." +
                                "\n\nRegister another user?");

                builder.setPositiveButton("Yes, Add New", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetForm();
                    }
                });

                builder.setNegativeButton("No, Go Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create().show();

//            Backendless.UserService.register(newUser, new AsyncCallback<BackendlessUser>() {
//                @Override
//                public void handleResponse(BackendlessUser response) {
//                    resetForm();
//                    showCustomToast(getApplicationContext(), toastView, getUserString(newUser)
//                    + " registered successfully.");
//
//                }
//
//                @Override
//                public void handleFault(BackendlessFault fault) {
//                    showCustomToast(getApplicationContext(), toastView, fault.getMessage());
//                }
//            });

            } else {
                showCustomToast(getApplicationContext(), toastView,
                        "Please make sure all required fields are filled.");
            }

        } else {
            showCustomToast(getApplicationContext(), toastView,
                    "Please make sure all required fields are filled.");
        }

    }

    private boolean isValidPersonalDetails() {

        boolean isValid = isValidFields(etName, etSurname,etEmail,  etPassword, etConfirm);

        if (isValid) {

            if (isEmailValid(etEmail)){

                if (!isPasswordsMatching(etPassword, etConfirm)) {

                    isValid = false;
                    showCustomToast(getApplicationContext(), toastView,
                            "Please make sure passwords match!");
                    etConfirm.requestFocus();
                }

            }else{
                isValid = false;
                showCustomToast(getApplicationContext(), toastView,
                        "Please enter valid email");
            }

        } else {

            showCustomToast(getApplicationContext(), toastView,
                    "Please enter required fields");

        }

        return isValid;
    }

    private boolean isValidStatDetails() {

        boolean isValid = isRadioChecked(rbFemale, rbMale) && isValidSpinner(spnCourse, spnEthnicity);
        if (isValid) {

            tvGender.setError(null);
            tvEthnicity.setError(null);
            tvCourse.setError(null);

        } else {

            if (!isRadioChecked(rbFemale, rbMale))
                tvGender.setError("Please specify gender");

            if (!isValidSpinner(spnCourse))
                tvCourse.setError("Please select Course on the dropdown list");

            if (!isValidSpinner(spnEthnicity))
                tvEthnicity.setError("Please select Ethnicity on the dropdown list");
        }
        return isValid;
    }

    private void resetForm()
    {

        clearFields(etEmail, etName, etSurname, etPassword, etConfirm);
        clearRadioGroup(rgGender);
        clearSpinners(spnCourse, spnEthnicity);
        switchViews(ivScanCard,ivCorrect);

        etPassword.setError(null);
        etConfirm.setError(null);
//        tvCourse.setError(null);
//        tvEthnicity.setError(null);
        tvGender.setError("");

        etEmail.requestFocus();

    }

    public void onClick_GoBack(View view) {
        onBackPressed();
    }
}