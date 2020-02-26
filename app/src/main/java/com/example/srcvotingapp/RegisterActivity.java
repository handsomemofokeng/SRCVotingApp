package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import static com.example.srcvotingapp.ApplicationClass.ROLE;
import static com.example.srcvotingapp.ApplicationClass.SURNAME;
import static com.example.srcvotingapp.ApplicationClass.clearFields;
import static com.example.srcvotingapp.ApplicationClass.clearRadioGroup;
import static com.example.srcvotingapp.ApplicationClass.clearSpinners;
import static com.example.srcvotingapp.ApplicationClass.buildAlertDialog;
import static com.example.srcvotingapp.ApplicationClass.getSelectedRadio;
import static com.example.srcvotingapp.ApplicationClass.getSpinnerValue;
import static com.example.srcvotingapp.ApplicationClass.getUserFullName;
import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.isEmailValid;
import static com.example.srcvotingapp.ApplicationClass.isPasswordsMatching;
import static com.example.srcvotingapp.ApplicationClass.isRadioChecked;
import static com.example.srcvotingapp.ApplicationClass.isValidFields;
import static com.example.srcvotingapp.ApplicationClass.isValidSpinner;
import static com.example.srcvotingapp.ApplicationClass.progressDialog;
import static com.example.srcvotingapp.ApplicationClass.scanStudentCard;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showProgressDialog;
import static com.example.srcvotingapp.ApplicationClass.showViews;
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
    //private Button btnRegister;
    private ImageView ivScanCard, ivCorrect;
    private LinearLayout frmStatisticalDetails;// frmPersonalDetails,


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

        hideViews(frmStatisticalDetails);

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (isStudent()) {
                    showViews(frmStatisticalDetails);
                } else {
                    hideViews(frmStatisticalDetails);
                }
            }
        });

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
                tvGender.setError(null);
            }
        });

    }

    private boolean isStudent() {
        return etEmail.getText().toString().endsWith("@stud.cut.ac.za");
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
        ivCorrect = findViewById(R.id.ivCorrectRegUser);
        ivScanCard = findViewById(R.id.ivScanCardRegUser);

//        btnRegister = findViewById(R.id.btnRegisterUser);
//        frmPersonalDetails = findViewById(R.id.frmPersonalDetails);
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

            newUser.setProperty(NAME, etName.getText().toString().trim());
            newUser.setProperty(SURNAME, etSurname.getText().toString().trim());
            newUser.setEmail(etEmail.getText().toString().trim());
            newUser.setPassword(etConfirm.getText().toString().trim());

            if (isStudent()) {

                newUser.setProperty(ROLE, "Student");
                newUser.setProperty(HAS_VOTED, false);
                newUser.setProperty(IS_CANDIDATE, false);

                if (isValidStatDetails()) {

                    newUser.setProperty(GENDER, getSelectedRadio(rbMale, rbFemale));
                    newUser.setProperty(ETHNICITY, getSpinnerValue(spnEthnicity));
                    newUser.setProperty(COURSE, getSpinnerValue(spnCourse));

                    registerNewUser();

                } else {
                    showCustomToast(getApplicationContext(), toastView,
                            getString(R.string.verify_fields));
                }
            } else {
                registerNewUser();
            }
        } else {
            showCustomToast(getApplicationContext(), toastView,
                    getString(R.string.verify_fields));
        }

    }

    private void registerNewUser() {

        showProgressDialog(RegisterActivity.this, "Registering New User",
                "Please wait while we register you to our App...", false);
        Backendless.UserService.register(newUser, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {

                progressDialog.dismiss();
                AlertDialog.Builder builder = buildAlertDialog(RegisterActivity.this,
                        "Registration Submitted", getUserFullName(newUser)
                                + " registered successfully." +
                                "\n\nPlease check your email for confirmation." +
                                "\n\nRegister another user?");

                builder.setPositiveButton("Yes, Add New",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetForm();
                            }
                        });

                builder.setNegativeButton("No, Sign In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create().show();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                progressDialog.dismiss();
                showMessageDialog("Error Registering", fault.getMessage());
//                showCustomToast(getApplicationContext(), toastView, fault.getMessage());
            }
        });
    }

    private boolean isValidPersonalDetails() {

        boolean isValid = isValidFields(etName, etSurname, etEmail, etPassword, etConfirm);

        if (isValid) {

            if (isEmailValid(etEmail)) {


                if (!isPasswordsMatching(etPassword, etConfirm)) {

                    isValid = false;
                    showMessageDialog("Passwords mismatch",
                            "Please make sure passwords match!");
                    etConfirm.requestFocus();
                }

            } else {
                isValid = false;
                showCustomToast(getApplicationContext(), toastView,
                        "Please enter a valid email");
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

            if (!isRadioChecked(rbFemale, rbMale)) {
                tvGender.setError("Please specify gender");
//                showCustomToast(this, toastView, tvGender.getError().toString());
            }

            if (!isValidSpinner(spnCourse)) {
                tvCourse.setError("Please select Course on the dropdown list");
//                showCustomToast(this, toastView, tvCourse.getError().toString());
            }
            if (!isValidSpinner(spnEthnicity)) {
                tvEthnicity.setError("Please select Ethnicity on the dropdown list");
//                showCustomToast(this, toastView, tvEthnicity.getError().toString());
            }
        }
        return isValid;
    }

    private void resetForm() {

        clearFields(etEmail, etName, etSurname, etPassword, etConfirm);
        clearRadioGroup(rgGender);
        clearSpinners(spnCourse, spnEthnicity);
        switchViews(ivScanCard, ivCorrect);
        hideViews(frmStatisticalDetails);

        etPassword.setError(null);
        etConfirm.setError(null);
//        tvCourse.setError(null);
//        tvEthnicity.setError(null);
        tvGender.setError("");

        etName.requestFocus();

    }

    public void onClick_GoBack(View view) {
        onBackPressed();
    }

//    public void onClick_AddPicture(View view) {
//        showCustomToast(this, toastView, "Show Picture Dialog... to be implemented");
//    }

    private void showMessageDialog(String title, String message) {
        AlertDialog.Builder builder = buildAlertDialog(
                RegisterActivity.this, title, message);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    public void onClick_AddPicture(View view) {
        showCustomToast(this, toastView, "To do: Take a picture or choose from directory");
    }
}