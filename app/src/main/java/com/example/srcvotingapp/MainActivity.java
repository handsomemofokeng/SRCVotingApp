package com.example.srcvotingapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.scanStudentCard;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showViews;

public class MainActivity extends AppCompatActivity {

    // UI references.
    private EditText etEmail;
    private EditText etPassword;
    private View toastView;
    private CheckBox chkRememberMe;
    private ImageView ivScanCard, ivSendResetLink;
    private Button btnSignIn, btnResetPassword, btnRegister;
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

    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout));
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        chkRememberMe = findViewById(R.id.chkRememberMe);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnRegister = findViewById(R.id.btn_register);
        btnResetPassword = findViewById(R.id.btn_reset);
        ivScanCard = findViewById(R.id.ivScanCard);
        ivSendResetLink = findViewById(R.id.ivSendResetLink);
        tilPassword = findViewById(R.id.tilPassword);

    }

    public void onClick_ScanCard(View view) {

        scanStudentCard(this);
        showCustomToast(getApplicationContext(), toastView, "Scan Student Card");
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
                etPassword.hasFocus();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onClick_SendResetLink(View view) {
        showLoginForm();
    }

    public void showLoginForm() {
        btnResetPassword.setText(R.string.action_reset_password);
        showViews(ivScanCard, tilPassword, chkRememberMe, btnSignIn);
        hideViews(ivSendResetLink);
    }

    public void showResetPasswordForm() {
        btnResetPassword.setText(R.string.action_go_back);
        hideViews(ivScanCard, tilPassword, chkRememberMe, btnSignIn);
        showViews(ivSendResetLink);
    }

    public void onClick_ResetPassword(View view) {

        if (btnResetPassword.getText().toString().equals(getString(R.string.action_reset_password))) {
            showResetPasswordForm();
        } else {
            showLoginForm();
        }

//        Backendless.UserService.restorePassword(etEmail.getText().toString().trim(),
//                new AsyncCallback<Void>() {
//                    @Override
//                    public void handleResponse(Void response) {
//
//                        showCustomToast(getApplicationContext(), toastView,
//                                "Reset link sent to "+ etEmail.getText().toString().trim());
//                       showLoginForm();
//                    }
//
//                    @Override
//                    public void handleFault(BackendlessFault fault) {
//                        showCustomToast(getApplicationContext(), toastView, fault.getMessage());
//                       showLoginForm();
//                    }
//                });

    }

    public void onClick_RegisterUser(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }
}
