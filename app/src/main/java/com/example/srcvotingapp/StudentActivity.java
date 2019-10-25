package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import static com.example.srcvotingapp.ApplicationClass.HAS_VOTED;
import static com.example.srcvotingapp.ApplicationClass.buildAlertDialog;
import static com.example.srcvotingapp.ApplicationClass.disableViews;
import static com.example.srcvotingapp.ApplicationClass.enableViews;
import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.progressDialog;
import static com.example.srcvotingapp.ApplicationClass.sessionUser;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showProgressDialog;
import static com.example.srcvotingapp.ApplicationClass.switchViews;

public class StudentActivity extends AppCompatActivity {

    // UI references.
    private EditText etEmail, etName, etSurname;
    TextInputLayout etPassword, etConfirm;
    private View toastView;
    private TextView tvCourse, tvEthnicity, tvGender;
    private Spinner spnEthnicity, spnCourse;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private Button btnRegister, btnGoBack;
    private ImageView ivScanCard, ivCorrect;
    private LinearLayout frmStatisticalDetails;// frmPersonalDetails,

    FloatingActionButton fabSave, fabEdit, fabVote, fabRestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        disableViews(etEmail, etName, etSurname, spnEthnicity, spnCourse, rbMale, rbFemale);

        hideViews(etPassword, etConfirm, ivScanCard, ivCorrect, btnRegister, btnGoBack, fabSave);

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableViews(etName, etSurname, spnEthnicity, spnCourse, rbMale, rbFemale);
                switchViews(fabSave, fabEdit);

            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableViews(etName, etSurname, spnEthnicity, spnCourse, rbMale, rbFemale);
                switchViews(fabEdit, fabSave);
            }
        });


        fabVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Boolean) sessionUser.getProperty(HAS_VOTED))
                    startActivity(new Intent(StudentActivity.this, ResultsActivity.class));
                else
                    startActivity(new Intent(StudentActivity.this, VoteActivity.class));
            }
        });

        fabRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = buildAlertDialog(StudentActivity.this,
                        "Restore Password", "Send Reset Password link to "
                                + etEmail.getText().toString().trim() + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showProgressDialog(StudentActivity.this, "Restore Password",
                                "Sending reset link to " + etEmail.getText().toString().trim(),
                                false);
                        Backendless.UserService.restorePassword(etEmail.getText().toString().trim(),
                                new AsyncCallback<Void>() {
                                    @Override
                                    public void handleResponse(Void response) {

                                        progressDialog.dismiss();
                                        showMessageDialog("Success", "Reset link sent to "
                                                + etEmail.getText().toString().trim() +
                                                ".\n\nPlease check your email for reset instructions.");

                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {

                                        progressDialog.dismiss();
                                        showMessageDialog("Reset Error", fault.getMessage());


                                    }
                                });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();

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
        etPassword = findViewById(R.id.tilPasswordReg);
        etConfirm = findViewById(R.id.tilConfirmPassword);
        spnCourse = findViewById(R.id.spnCourseReg);
        spnEthnicity = findViewById(R.id.spnEthnicityReg);
        tvCourse = findViewById(R.id.tvCourse);
        tvEthnicity = findViewById(R.id.tvEthnicity);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        ivCorrect = findViewById(R.id.ivCorrectRegUser);
        ivScanCard = findViewById(R.id.ivScanCardRegUser);
        btnRegister = findViewById(R.id.btnRegisterUser);
        btnGoBack = findViewById(R.id.btnGoBack);
//        frmPersonalDetails = findViewById(R.id.frmPersonalDetails);
        frmStatisticalDetails = findViewById(R.id.frmStatisticalDetails);

        fabEdit = findViewById(R.id.fabEdit);
        fabSave = findViewById(R.id.fabSave);
        fabVote = findViewById(R.id.fabVote);
        fabRestore = findViewById(R.id.fabRestorePassword);
    }

    public void onClick_AddPicture(View view) {
        showCustomToast(this, toastView, "Show Picture Dialog");
    }


    private void showMessageDialog(String title, String message) {
        AlertDialog.Builder builder = buildAlertDialog(
                StudentActivity.this, title, message);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }
}
