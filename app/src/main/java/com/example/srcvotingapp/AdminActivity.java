package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import static com.example.srcvotingapp.ApplicationClass.NAME;
import static com.example.srcvotingapp.ApplicationClass.SURNAME;
import static com.example.srcvotingapp.ApplicationClass.buildAlertDialog;
import static com.example.srcvotingapp.ApplicationClass.clearErrors;
import static com.example.srcvotingapp.ApplicationClass.disableViews;
import static com.example.srcvotingapp.ApplicationClass.enableViews;
import static com.example.srcvotingapp.ApplicationClass.getUserFullName;
import static com.example.srcvotingapp.ApplicationClass.getUserString;
import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.isValidFields;
import static com.example.srcvotingapp.ApplicationClass.progressDialog;
import static com.example.srcvotingapp.ApplicationClass.sessionUser;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showProgressDialog;
import static com.example.srcvotingapp.ApplicationClass.showViews;
import static com.example.srcvotingapp.ApplicationClass.switchViews;

public class AdminActivity extends AppCompatActivity {

    View toastView;
    private EditText etEmail, etName, etSurname;
    FloatingActionButton fabSave, fabEdit, fabResults, fabRestore, fabCancel, fabManageParties;//,
            //fabStartElections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), "Admin Menu",
                    getUserFullName(sessionUser));

        initViews();

        disableForm();

        populateForm();

        //Register Parties
//        List<Party> partyList = new ArrayList<>();
//
//        partyList.add(new Party("Economic Freedom Fighters Students' Command", "EFFSC"));
//
//        partyList.add(new Party("Democratic Alliance Student Organisation", "DASO"));
//
//        partyList.add(new Party("South African Student Congress", "SASCO"));
//
//        showProgressDialog(AdminActivity.this, "Registering Parties",
//                "Please wait while we register selected Party(s)...", false);
//
//        Backendless.Data.of(Party.class).create(partyList, new AsyncCallback<List<String>>() {
//            @Override
//            public void handleResponse(List<String> response) {
//                progressDialog.dismiss();
//                showMessageDialog("Registering Successful",
//                        "Party(s) registered successfully.");
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                progressDialog.dismiss();
//                showMessageDialog("Registering Error", fault.getMessage());
//            }
//        });
    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));
        etEmail = findViewById(R.id.etEmailAdmin);
        etName = findViewById(R.id.etNameAdmin);
        etSurname = findViewById(R.id.etSurnameAdmin);

        fabEdit = findViewById(R.id.fabEditAdmin);
        fabSave = findViewById(R.id.fabSaveAdmin);
        fabResults = findViewById(R.id.fabResultsAdmin);
        fabRestore = findViewById(R.id.fabRestorePasswordAdmin);
        fabCancel = findViewById(R.id.fabCancelEditAdmin);
        fabManageParties = findViewById(R.id.fabAddParty);
//        fabStartElections = findViewById(R.id.fabStartElections);
    }

    @Override
    public void onBackPressed() {
        showSignOutDialog();
    }

    private void showSignOutDialog() {
        AlertDialog.Builder builder = buildAlertDialog(AdminActivity.this,
                "Sign Out", sessionUser.getEmail() + " is about to be signed out\n" +
                        "\nContinue signing out?");

        builder.setPositiveButton("Yes, Sign Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logOutUser();
            }
        });

        builder.setNegativeButton("No, Stay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create().show();
    }

    private void showMessageDialog(String title, String message) {
        AlertDialog.Builder builder = buildAlertDialog(
                AdminActivity.this, title, message);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    public void logOutUser() {

        try {
            showProgressDialog(AdminActivity.this, "Signing Out",
                    "Please wait...", false);
            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void aVoid) {
                    progressDialog.dismiss();
                    showCustomToast(getApplicationContext(), toastView,
                            sessionUser.getEmail()
                                    + " signed out successfully.");

//                    commitMyPrefs(sessionUser.getEmail(), sessionUser.getPassword(), false);

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
        if (item.getItemId() == R.id.ab_sign_off) {
            showSignOutDialog();
        } else {
            showCustomToast(AdminActivity.this, toastView,
                    "Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick_ViewResults(View view) {
        startActivity(new Intent(getApplicationContext(), ResultsActivity.class));
    }

    public void onClick_AddCandidate(View view) {

        startActivity(new Intent(getApplicationContext(), AddCandidateActivity.class));

    }

//    public void onClick_StartElections(View view) {
//        // TODO: 2019/09/25 send notifications to students and set the timer
//        TimePicker timePicker = new TimePicker(AdminActivity.this);
//        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//
//            }
//        });
//
//    }

    public void onClick_ManageParties(View view) {
        startActivity(new Intent(AdminActivity.this, AddCandidateActivity.class));
    }

    public void onClick_SendResetLink(View view) {
        AlertDialog.Builder builder = buildAlertDialog(AdminActivity.this,
                "Restore Password", "Send Reset Password link to "
                        + sessionUser.getEmail() + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showProgressDialog(AdminActivity.this, "Restore Password",
                        "Sending reset link to " +
                                etEmail.getText().toString().trim(), false);
                Backendless.UserService.restorePassword(etEmail.getText().toString().trim(),
                        new AsyncCallback<Void>() {
                            @Override
                            public void handleResponse(Void response) {

                                progressDialog.dismiss();
                                showMessageDialog("Success", "Reset link " +
                                        "sent to " + sessionUser.getEmail() + ".\n\n" +
                                        "Please check your email for reset instructions.");

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

    private void updateUser() {

        sessionUser.setProperty(NAME, etName.getText().toString().trim());
        sessionUser.setProperty(SURNAME, etSurname.getText().toString().trim());

        showProgressDialog(AdminActivity.this, "Update User",
                "Please wait while we update your details...", false);
        Backendless.Data.of(BackendlessUser.class).save(sessionUser,
                new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        progressDialog.dismiss();
                        showCustomToast(AdminActivity.this, toastView,
                                getUserString(response) + " updated successfully.");
                        sessionUser = response;
                        populateForm();
                        disableForm();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        progressDialog.dismiss();
                        showMessageDialog("Error Updating", fault.getMessage());
                    }
                });
    }

    private void populateForm() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), "Admin Menu",
                    getUserFullName(sessionUser));

        etEmail.setText(sessionUser.getEmail());
        etName.setText(sessionUser.getProperty(NAME).toString().trim());
        etSurname.setText(sessionUser.getProperty(SURNAME).toString().trim());
        clearErrors(etEmail, etName, etSurname);
    }

    private void enableForm() {
        enableViews(etName, etSurname);
        switchViews(fabSave, fabEdit);
        hideViews(fabResults, fabRestore, fabManageParties);//, fabStartElections);
        showViews(fabCancel);
        etName.requestFocus();
    }

    private void disableForm() {
        disableViews(etEmail, etName, etSurname);
        switchViews(fabEdit, fabSave);
        hideViews(fabCancel);
        showViews(fabResults, fabRestore, fabManageParties);//, fabStartElections);
    }

    public void onClick_CancelEdit(View view) {
        disableForm();
        populateForm();
    }

    public void onClick_EditUser(View view) {
        enableForm();
    }

    public void onClick_SaveUser(View view) {
        if (isValidFields(etName, etSurname)) {

            AlertDialog.Builder builder = buildAlertDialog(AdminActivity.this,
                    "Update User", "Are you sure you want to update details" +
                            " for " + sessionUser.getEmail() + "?");
            builder.setPositiveButton("Yes, Update",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateUser();
                        }
                    });

            builder.setNegativeButton("No, Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            disableForm();
                            populateForm();
                        }
                    });
            builder.create().show();
        }
    }
}