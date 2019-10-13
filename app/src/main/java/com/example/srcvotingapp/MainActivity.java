package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.example.srcvotingapp.ApplicationClass.EMAIL;
import static com.example.srcvotingapp.ApplicationClass.HAS_VOTED;
import static com.example.srcvotingapp.ApplicationClass.MY_SHARED_PREFERENCES_NAME;
import static com.example.srcvotingapp.ApplicationClass.PASSWORD;
import static com.example.srcvotingapp.ApplicationClass.REMEMBER_ME;
import static com.example.srcvotingapp.ApplicationClass.ROLE;
import static com.example.srcvotingapp.ApplicationClass.buildAlertDialog;
import static com.example.srcvotingapp.ApplicationClass.clearFields;
import static com.example.srcvotingapp.ApplicationClass.currentUserPassword;
import static com.example.srcvotingapp.ApplicationClass.currentUsername;
import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.isEmailValid;
import static com.example.srcvotingapp.ApplicationClass.isPasswordValid;
import static com.example.srcvotingapp.ApplicationClass.isPhoneConnected;
import static com.example.srcvotingapp.ApplicationClass.isValidFields;
import static com.example.srcvotingapp.ApplicationClass.myPrefs;
import static com.example.srcvotingapp.ApplicationClass.progressDialog;
import static com.example.srcvotingapp.ApplicationClass.rememberMe;
import static com.example.srcvotingapp.ApplicationClass.scanStudentCard;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showProgressDialog;
import static com.example.srcvotingapp.ApplicationClass.showViews;
import static com.example.srcvotingapp.ApplicationClass.switchViews;

public class MainActivity extends AppCompatActivity {

    // UI references.
    private EditText etEmail;
    private EditText etPassword;
    private View toastView;
    private TextView tvResetLink;
    private CheckBox chkRememberMe;
    private ImageView ivScanCard, ivSendResetLink, ivSignIn, ivCorrect;
    private Button btnResetPassword;//, btnRegister;
    private TextInputLayout tilPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), getResources().getString(R.string.app_name),
                    "Authenticate User");

        //Get credentials on background
        new GetDataInBackground().execute();

        getMyPrefs();

        if (isPasswordValid(etPassword.getText().toString().trim())) {
            showViews(ivSignIn);
        }

        chkRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isEmailValid(etEmail) &&
                        isPasswordValid(etPassword.getText().toString().trim())) {

                    if (isChecked) {
                        commitMyPrefs(etEmail.getText().toString().trim(),
                                etPassword.getText().toString().trim(),
                                chkRememberMe.isChecked());
                    } else {
                        commitMyPrefs("", "", false);
                    }

                } else {
                    showCustomToast(MainActivity.this, toastView,
                            "Please make sure all fields are correct.");
                    chkRememberMe.setChecked(false);
                }
            }
        });

        if (rememberMe && isPhoneConnected(MainActivity.this)) {
            if (!(currentUsername.isEmpty() || currentUserPassword.isEmpty())) {
                progressDialog.setMessage("Logging in, please wait...");
                attemptLogIn();
            }
        }

        // TODO: 2019/10/06 Authenticate Admin
        ivSignIn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
                return false;
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //showScanButton();
//                switchViews(ivScanCard, ivCorrect);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //showScanButton();
//                switchViews(ivScanCard, ivCorrect);
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (btnResetPassword.getText().toString().equals(getString(R.string.action_go_back))) {
                    hideViews(ivCorrect);
                    if (isEmailValid(etEmail)) {
                        //showSearchButton();
                        switchViews(ivSendResetLink, ivScanCard);
                        if (isPasswordValid(etPassword.getText().toString()))
                            showViews(ivSignIn);
                        else
                            hideViews(ivSignIn);

                    } else {
                        //showScanButton();
                        switchViews(ivScanCard, ivSendResetLink);
                        hideViews(ivSignIn);
                    }

                } else {
                    if (isEmailValid(etEmail)) {
                        //showSearchButton();
                        switchViews(ivCorrect, ivScanCard);
                        if (isPasswordValid(etPassword.getText().toString()))
                            showViews(ivSignIn);
                        else
                            hideViews(ivSignIn);

                    } else {
                        //showScanButton();
                        switchViews(ivScanCard, ivCorrect);
                        hideViews(ivSignIn);
                    }
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isPasswordValid(s.toString())) {
                    etPassword.setError(null);
                    if (isEmailValid(etEmail))
                        showViews(ivSignIn);
                    else
                        hideViews(ivSignIn);
                } else {
                    hideViews(ivSignIn);
                    etPassword.setError("Password length must be > 2");
                }
            }
        });

    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast, (
                ViewGroup) findViewById(R.id.toast_layout));
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        chkRememberMe = findViewById(R.id.chkRememberMe);
        tvResetLink = findViewById(R.id.tvResetLink);
        ivSignIn = findViewById(R.id.ivSignIn);
        ivCorrect = findViewById(R.id.ivCorrect);
//        btnRegister = findViewById(R.id.btn_register);
        btnResetPassword = findViewById(R.id.btn_reset);
        ivScanCard = findViewById(R.id.ivScanCard);
        ivSendResetLink = findViewById(R.id.ivSendResetLink);
        tilPassword = findViewById(R.id.tilPassword);

    }

    public void onClick_ScanCard(View view) {

        scanStudentCard(MainActivity.this);
        showCustomToast(MainActivity.this, toastView, "Scan Student Card");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                showCustomToast(MainActivity.this, toastView, "Result not found");
                etEmail.requestFocus();
            } else {
                etEmail.setText(String.format("%s@stud.cut.ac.za", result.getContents()));
                etEmail.setError(null);
                etPassword.requestFocus();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onClick_SendResetLink(View view) {

        if (isValidFields(etEmail) && isEmailValid(etEmail)) {
            // TODO: 2019/08/27 Send Reset link
            showCustomToast(MainActivity.this, toastView,
                    "Reset  link sent to " + etEmail.getText().toString().trim());

            showProgressDialog(MainActivity.this, "Reset Password",
                    String.format("Sending reset link to %s...", etEmail.getText().toString()),
                    true);

            showLoginForm();
            clearFields(etPassword);
            etPassword.requestFocus();
        } else {
            showCustomToast(MainActivity.this, toastView,
                    "Please enter valid email");
        }

    }

    private void showLoginForm() {
        btnResetPassword.setText(R.string.action_reset_password);

        showViews(ivScanCard, tilPassword, chkRememberMe);
        hideViews(ivSendResetLink, tvResetLink);
    }

    private void showResetPasswordForm() {

        btnResetPassword.setText(R.string.action_go_back);

        hideViews(ivScanCard, tilPassword, chkRememberMe, ivSignIn, ivCorrect);
        showViews(ivSendResetLink, tvResetLink);
    }

    public void onClick_ResetPassword(View view) {

        if (btnResetPassword.getText().toString().equals(getString(R.string.action_reset_password))) {
            showResetPasswordForm();
            etPassword.setText("");
        } else {
            showLoginForm();
        }

//        Backendless.UserService.restorePassword(etEmail.getText().toString().trim(),
//                new AsyncCallback<Void>() {
//                    @Override
//                    public void handleResponse(Void response) {
//
//                        showCustomToast(MainActivity.this, toastView,
//                                "Reset link sent to "+ etEmail.getText().toString().trim());
//                       showLoginForm();
//                    }
//
//                    @Override
//                    public void handleFault(BackendlessFault fault) {
//                        showCustomToast(MainActivity.this, toastView, fault.getMessage());
//                       showLoginForm();
//                    }
//                });

    }

    public void onClick_RegisterUser(View view) {
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

    public void onClick_SignIn(View view) {
        // TODO: 2019/10/14 attemptLogIn();
        startActivity(new Intent(MainActivity.this, VoteActivity.class));
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = buildAlertDialog(MainActivity.this,
                "Exit Application", "Are you sure you want to exit application?");

        builder.setPositiveButton("Yes, Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.super.onBackPressed();
//                finish();
            }
        });

        builder.setNegativeButton("No, Stay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

    }

    public void attemptLogIn() {

        if (isPhoneConnected(MainActivity.this)) {
            showProgressDialog(MainActivity.this, "Logging In",
                    "Please wait while we log you in...", true);
            Backendless.UserService.login(etEmail.getText().toString().trim(),
                    etPassword.getText().toString().trim(), new AsyncCallback<BackendlessUser>() {

                        @Override
                        public void handleResponse(BackendlessUser response) {

                            ApplicationClass.sessionUser = response;
                            if (chkRememberMe.isChecked()) {

                                getMyPrefs();
                                commitMyPrefs(etEmail.getText().toString().trim(),
                                        etPassword.getText().toString().trim(),
                                        chkRememberMe.isChecked());
                            }

                            if (response.getProperty(ROLE).toString().equals("Admin")) {
                                startActivity(new Intent(MainActivity.this,
                                        AdminActivity.class));
                            } else {
                                if (response.getProperty(ROLE).toString().equals("Student")) {
                                    if (!(boolean) response.getProperty(HAS_VOTED)) {
                                        startActivity(new Intent(MainActivity.this,
                                                VoteActivity.class));
                                    } else {
                                        startActivity(new Intent(MainActivity.this,
                                                ResultsActivity.class));
                                    }

                                } else {
                                    showUnauthorizedDialog();
                                }
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            progressDialog.dismiss();
                            showCustomToast(MainActivity.this, toastView, "Error: "
                                    + fault.getMessage());
                        }
                    });
        } else {
            AlertDialog.Builder builder = buildAlertDialog(MainActivity.this,
                    "Connection Failure",
                    "Please make sure WiFi or Mobile data is enabled.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }
    }

    private void showUnauthorizedDialog() {
        AlertDialog.Builder builder = buildAlertDialog(
                MainActivity.this, "Unauthorized Access",
                "Hmmm... seems like you haven't been granted access to use this App." +
                        "\n\nTry entering credentials again?");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    private void commitMyPrefs(String username, String password, boolean rememberMe) {
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putBoolean(REMEMBER_ME, rememberMe);
        editor.putString(EMAIL, username);
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    /**
     * Async Task to get settings on the background before executing
     */
    private class GetDataInBackground extends AsyncTask<Void, Void, Void> {

        private String errors;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {

                showProgressDialog(MainActivity.this, "Initializing",
                        "Getting your settings, please wait...", false);

            } catch (Exception ex) {

                showCustomToast(MainActivity.this, toastView,
                        "Error: " + ex.getMessage());
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            progressDialog.setMessage("Getting things ready...");
            Backendless.Data.of(BackendlessUser.class).getObjectCount(new AsyncCallback<Integer>() {

                @Override
                public void handleResponse(Integer integer) {

                    getMyPrefs();
                    progressDialog.dismiss();
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                    errors += "Settings Error: " + backendlessFault.getMessage() + "\n";
                    showCustomToast(MainActivity.this, toastView, errors);
                    progressDialog.dismiss();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }

    /**
     * Create and retrieve Shared Preferences
     */
    private void getMyPrefs() {

        myPrefs = getSharedPreferences(MY_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        rememberMe = myPrefs.getBoolean(REMEMBER_ME, false);
        currentUsername = myPrefs.getString(EMAIL, "");
        currentUserPassword = myPrefs.getString(PASSWORD, "");

        etEmail.setText(currentUsername);
        etPassword.setText(currentUserPassword);
        chkRememberMe.setChecked(rememberMe);

    }
}
