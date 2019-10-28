package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import static com.example.srcvotingapp.ApplicationClass.EMAIL;
import static com.example.srcvotingapp.ApplicationClass.HAS_VOTED;
import static com.example.srcvotingapp.ApplicationClass.MY_SHARED_PREFERENCES_NAME;
import static com.example.srcvotingapp.ApplicationClass.NAME;
import static com.example.srcvotingapp.ApplicationClass.PASSWORD;
import static com.example.srcvotingapp.ApplicationClass.REMEMBER_ME;
import static com.example.srcvotingapp.ApplicationClass.SURNAME;
import static com.example.srcvotingapp.ApplicationClass.buildAlertDialog;
import static com.example.srcvotingapp.ApplicationClass.commitMyPrefs;
import static com.example.srcvotingapp.ApplicationClass.currentUserPassword;
import static com.example.srcvotingapp.ApplicationClass.currentUsername;
import static com.example.srcvotingapp.ApplicationClass.disableViews;
import static com.example.srcvotingapp.ApplicationClass.enableViews;
import static com.example.srcvotingapp.ApplicationClass.getUserFullName;
import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.myPrefs;
import static com.example.srcvotingapp.ApplicationClass.progressDialog;
import static com.example.srcvotingapp.ApplicationClass.rememberMe;
import static com.example.srcvotingapp.ApplicationClass.sessionUser;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showProgressDialog;
import static com.example.srcvotingapp.ApplicationClass.switchViews;

public class StudentActivity extends AppCompatActivity {

    // UI references.
    private EditText etEmail, etName, etSurname;
    private TextInputLayout etPassword, etConfirm;
    private View toastView;
    private TextView tvCourse, tvEthnicity, tvGender;
    private Spinner spnEthnicity, spnCourse;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private Button btnRegister, btnGoBack;
    private ImageView ivScanCard, ivCorrect, ivAddPhoto;
    private LinearLayout frmStatisticalDetails;// frmPersonalDetails,

    FloatingActionButton fabSave, fabEdit, fabVote, fabRestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), getResources().getString(R.string.title_activity_student),
                    getUserFullName(sessionUser));

        initViews();

        etName.clearFocus();
        disableViews(etEmail, etName, etSurname, spnEthnicity, spnCourse, rbMale, rbFemale);

        etEmail.setText(sessionUser.getEmail());
        etName.setText(sessionUser.getProperty(NAME).toString().trim());
        etSurname.setText(sessionUser.getProperty(SURNAME).toString().trim());


        hideViews(etPassword, etConfirm, ivScanCard, ivCorrect, btnRegister, btnGoBack, fabSave);//,                ivAddPhoto);

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
                                + sessionUser.getEmail() + "?");
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
                                                + sessionUser.getEmail() +
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
        ivAddPhoto = findViewById(R.id.ivAddPicture);
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

    @Override
    public void onBackPressed() {
        showSignOutDialog();
    }

    private void showSignOutDialog() {
        AlertDialog.Builder builder = buildAlertDialog(StudentActivity.this,
                "Sign Out", sessionUser.getEmail() + " is about to be signed out\n" +
                        "\nContinue signing out?");

        builder.setPositiveButton("Yes, Sign Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                StudentActivity.super.onBackPressed();
                logOutUser();
            }
        });

        builder.setNegativeButton("No, Stay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void logOutUser() {

        try {
            showProgressDialog(StudentActivity.this, "Signing Out",
                    "Please wait...", false);
            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void aVoid) {
                    progressDialog.dismiss();
                    showCustomToast(getApplicationContext(), toastView,
                            sessionUser.getEmail()
                                    + " signed out successfully.");

//                    commitMyPrefs("", "", false);
                    finish();
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                    progressDialog.dismiss();
                    showCustomToast(getApplicationContext(), toastView, "Error: "
                            + backendlessFault.getMessage());
                }
            });
        } catch (Exception ex) {
            showCustomToast(getApplicationContext(), toastView, "Error: " + ex.getMessage());
        }

        //return feedBack;
    }//end method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ab_sign_off:
                showSignOutDialog();
                break;
            default:
                showCustomToast(StudentActivity.this, toastView,
                        "Unexpected value: " + item.getItemId());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
