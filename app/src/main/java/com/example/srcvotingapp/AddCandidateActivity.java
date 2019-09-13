package com.example.srcvotingapp;

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
import android.widget.Toast;

import com.backendless.persistence.DataQueryBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.example.srcvotingapp.ApplicationClass.PARTY_ID;
import static com.example.srcvotingapp.ApplicationClass.Portfolios;
import static com.example.srcvotingapp.ApplicationClass.clearFields;
import static com.example.srcvotingapp.ApplicationClass.clearRadioGroup;
import static com.example.srcvotingapp.ApplicationClass.clearSpinners;
import static com.example.srcvotingapp.ApplicationClass.getSelectedRadio;
import static com.example.srcvotingapp.ApplicationClass.getSpinnerValue;
import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.isEmailValid;
import static com.example.srcvotingapp.ApplicationClass.isValidSpinner;
import static com.example.srcvotingapp.ApplicationClass.scanStudentCard;
import static com.example.srcvotingapp.ApplicationClass.selectQuery;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showViews;
import static com.example.srcvotingapp.ApplicationClass.switchViews;
import static com.example.srcvotingapp.ApplicationClass.uncheckRadioButton;

public class AddCandidateActivity extends AppCompatActivity {

    //UI references
    View toastView;
    EditText etEmail, etName;
    TextView tvSelectedPortfolio;
    ImageView ivScanCard, ivSearch, ivResetParty, ivEditCandidate, ivSaveCandidate;
    RadioGroup rgCandidatePartyRegCan;
    RadioButton rbEFFSC, rbDASO, rbSASCO;
    Spinner spnPortfolio;
    LinearLayout frmParty, frmCandidateDetails, frmCandidateName, frmSearchemail;


    Party selectedParty;
    private DataQueryBuilder queryParty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_candidate);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), getResources().getString(R.string.app_name),
                    "Register New Candidate");

        initViews();

        hideViews(frmParty, frmCandidateDetails);

        rgCandidatePartyRegCan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                hideViews(rbEFFSC, rbDASO, rbSASCO);
                showViews(ivResetParty, findViewById(checkedId), frmParty);

                selectedParty = null;

                switch (checkedId) {

                    case R.id.rbEFFSCRegCan:

                        selectedParty = new Party("Economic Freedom Fighters Students' Command",
                                "EFFSC");

                        break;

                    case R.id.rbDASORegCan:

                        selectedParty = new Party("Democratic Alliance Student Organisation",
                                "DASO");

                        break;

                    case R.id.rbSASCORegCan:

                        selectedParty = new Party("South African Student Congress",
                                "SASCO");

                        break;
                }

                if (selectedParty != null) {

                    queryParty = selectQuery(PARTY_ID, selectedParty.getPartyID(), PARTY_ID);
                    // TODO: 2019/09/11 Get Selected Party from Backendless

                }


            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //showScanButton();
                switchViews(ivScanCard, ivSearch);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //showScanButton();
                switchViews(ivScanCard, ivSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEmailValid(etEmail)) {
                    //showSearchButton();
                    switchViews(ivSearch, ivScanCard);

                } else {
                    //showScanButton();
                    switchViews(ivScanCard, ivSearch);
                }
            }
        });

        spnPortfolio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {

                    showViews(frmCandidateDetails, frmCandidateName);
                    hideViews(frmSearchemail);

                    tvSelectedPortfolio.setText(Portfolios[position - 1]);
                    switch (position) {

                        case 1:
                            etName.setText(selectedParty.getPresident());
                            break;

                        case 2:

                            etName.setText(selectedParty.getDeputyPresident());
                            break;

                        case 3:

                            etName.setText(selectedParty.getSecretaryGeneral());
                            break;

                        case 4:
                            etName.setText(selectedParty.getFinancialOfficer());
                            break;

                        case 5:
                            etName.setText(selectedParty.getConstitutionalAndLegalAffairs());
                            break;

                        case 6:
                            etName.setText(selectedParty.getSportsOfficer());
                            break;

                        case 7:
                            etName.setText(selectedParty.getPublicRelationsOfficer());
                            break;

                        case 8:
                            etName.setText(selectedParty.getHealthAndWelfareOfficer());
                            break;

                        case 9:
                            etName.setText(selectedParty.getProjectsAndCampaignOfficer());
                            break;

                        case 10:
                            etName.setText(selectedParty.getStudentAffairs());
                            break;

                        case 11:
                            etName.setText(selectedParty.getEquityAndDiversityOfficer());
                            break;

                        case 12:
                            etName.setText(selectedParty.getTransformationOfficer());
                            break;

                        default:

                            etName.setText(null);
                            break;

                    }

                } else {

                    hideViews(frmCandidateDetails);
                    etName.setText(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));
        etEmail = findViewById(R.id.etEmailRegCan);
        etName = findViewById(R.id.etNameRegCan);
        ivScanCard = findViewById(R.id.ivScanCardRegCan);
        ivSearch = findViewById(R.id.ivSearchRegCan);
        ivEditCandidate = findViewById(R.id.ivEditCandidateRegCan);
        ivSaveCandidate = findViewById(R.id.ivSaveCandidateRegCan);
        tvSelectedPortfolio = findViewById(R.id.tvSelectedPortfolioRegCan);

        frmParty = findViewById(R.id.frmPartyRegCan);
        frmCandidateDetails = findViewById(R.id.frmCandidateDetails);
        frmCandidateName = findViewById(R.id.frmCandidateNameRegCan);
        frmSearchemail = findViewById(R.id.frmSearchEmailRegCan);

        rgCandidatePartyRegCan = findViewById(R.id.rgCandidateRegCan);
        rbDASO = findViewById(R.id.rbDASORegCan);
        rbEFFSC = findViewById(R.id.rbEFFSCRegCan);
        rbSASCO = findViewById(R.id.rbSASCORegCan);

        ivResetParty = findViewById(R.id.ivResetParty);

        spnPortfolio = findViewById(R.id.spnPortfolioRegCan);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                showCustomToast(getApplicationContext(), toastView, "Result not found");
                etEmail.requestFocus();
            } else {

                etEmail.setText(String.format("%s@stud.cut.ac.za", result.getContents()));
                etEmail.setError(null);
                //showSearchButton();
                switchViews(ivSearch, ivScanCard);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

//    private void showScanButton() {
//        showViews(ivScanCard);
//        hideViews(ivSearch);
//    }
//
//    private void showSearchButton() {
//        showViews(ivSearch);
//        hideViews(ivScanCard);
//    }

    public void onClick_ScanCard(View view) {

        scanStudentCard(this);
        showCustomToast(getApplicationContext(), toastView, "Scan Student Card");
    }

    public void onClick_SearchEmail(View view) {
        // TODO: 2019/09/10 Search by Email

    }

    public void onClick_ResetPartySelection(View view) {

        uncheckRadioButton(rbEFFSC, rbDASO, rbSASCO);
        showViews(rbEFFSC, rbDASO, rbSASCO);
        clearSpinners(spnPortfolio);
        hideViews(view, frmParty, frmCandidateDetails);
        clearFields(etName);

    }

    public void onClick_EditPortfolio(View view) {
        // TODO: 2019/09/11 Search By Email

        switchViews(frmSearchemail, frmCandidateName);
        clearFields(etEmail);

    }

    public void onClick_Navigate(View view) {
        switchViews(frmCandidateName, frmSearchemail);
    }
}
