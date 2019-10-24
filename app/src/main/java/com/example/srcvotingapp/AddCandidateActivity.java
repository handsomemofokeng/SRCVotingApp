package com.example.srcvotingapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.backendless.persistence.DataQueryBuilder;
import com.example.srcvotingapp.BL.Party;
import com.example.srcvotingapp.BL.PartyAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import static com.example.srcvotingapp.ApplicationClass.PARTY_ID;
import static com.example.srcvotingapp.ApplicationClass.Portfolios;
import static com.example.srcvotingapp.ApplicationClass.clearFields;
import static com.example.srcvotingapp.ApplicationClass.getUserString;
import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.isEmailValid;
import static com.example.srcvotingapp.ApplicationClass.scanStudentCard;
import static com.example.srcvotingapp.ApplicationClass.selectQuery;
import static com.example.srcvotingapp.ApplicationClass.sessionUser;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showViews;
import static com.example.srcvotingapp.ApplicationClass.switchViews;
import static com.example.srcvotingapp.ApplicationClass.uncheckRadioButton;
import static com.example.srcvotingapp.ApplicationClass.validateEmailInput;

public class AddCandidateActivity extends AppCompatActivity implements PartyAdapter.ItemClicked {

    //UI references
    View toastView;
    EditText etEmail, etFoundCandidateName, etFoundCandidateCourse;
    TextView tvSelectedPortfolio;
    ImageView ivScanCard, ivSearch, ivResetParty;
    RadioGroup rgCandidatePartyRegCan;
    RadioButton rbEFFSC, rbDASO, rbSASCO;
    LinearLayout frmParty, frmCandidateDetails, frmSearchEmail, frmFoundCandidate;//  frmCandidateName,

    //RecyclerView References
    RecyclerView rvCandidates;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> candidates;

    private Party selectedParty;
    private DataQueryBuilder queryParty;
    private String selectedPortfolio = "";
    private int selectedPosition = -1;
    private String selectedCandidate =",";
    String foundCandidate = ",";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_candidate);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), getResources().getString(R.string.app_name),
                    getUserString(sessionUser));

        initViews();

        resetForm();
//        hideViews(frmCandidateDetails);//frmParty,
//
//        etEmail.setError(null);

        // TODO: 2019/10/18 Get Party from Backendless
        rgCandidatePartyRegCan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                hideViews(rbEFFSC, rbDASO, rbSASCO, frmFoundCandidate);

                showViews(ivResetParty, findViewById(checkedId), frmParty, rvCandidates);

                selectedParty = null;


                switch (checkedId) {

                    case R.id.rbEFFSCRegCan:

                        selectedPortfolio = "EFFSC";
                        // TODO: 2019/10/24 Remove Below!
                        selectedParty = new Party("Economic Freedom Fighters Students' Command",
                                "EFFSC");

                        break;

                    case R.id.rbDASORegCan:

                        selectedPortfolio = "DASO";
                        // TODO: 2019/10/24 Remove Below!
                        selectedParty = new Party("Democratic Alliance Student Organisation",
                                "DASO");

                        break;

                    case R.id.rbSASCORegCan:

                        selectedPortfolio = "SASCO";
                        // TODO: 2019/10/24 Remove Below!
                        selectedParty = new Party("South African Student Congress",
                                "SASCO");

                        break;
                }

                if (selectedParty != null) {

                    queryParty = selectQuery(PARTY_ID, selectedParty.getPartyID(), PARTY_ID);

                    layoutManager = new LinearLayoutManager(AddCandidateActivity.this,
                            LinearLayout.VERTICAL, false);

                    rvCandidates.setLayoutManager(layoutManager);

                    candidates = new ArrayList<>();

                    candidates.add(selectedParty.getPresident());
                    candidates.add(selectedParty.getDeputyPresident());
                    candidates.add(selectedParty.getSecretaryGeneral());
                    candidates.add(selectedParty.getFinancialOfficer());
                    candidates.add(selectedParty.getConstitutionalAndLegalAffairs());
                    candidates.add(selectedParty.getSportsOfficer());
                    candidates.add(selectedParty.getPublicRelationsOfficer());
                    candidates.add(selectedParty.getHealthAndWelfareOfficer());
                    candidates.add(selectedParty.getProjectsAndCampaignOfficer());
                    candidates.add(selectedParty.getStudentAffairs());
                    candidates.add(selectedParty.getEquityAndDiversityOfficer());
                    candidates.add(selectedParty.getTransformationOfficer());

                    myAdapter = new PartyAdapter(AddCandidateActivity.this, candidates);
                    rvCandidates.setAdapter(myAdapter);

                    // TODO: 2019/09/11 Get Selected Party from Backendless
//                    tvPartyDetails.setText(selectedParty.toString());

                }

            }
        });

        validateEmailInput(etEmail, ivScanCard, ivSearch);

//        spnPortfolio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                if (position > 0 && position < spnPortfolio.getAdapter().getCount())
//                    showViews(btnNext, btnPrevious);
//
//                if (position > 0) {
//
//                    showViews(frmCandidateDetails, frmCandidateName);
//                    hideViews(frmSearchEmail, frmFoundCandidate);
//
//                    tvSelectedPortfolio.setText(Portfolios[position - 1]);
//
//                    etName.setText(selectedParty.getCandidateByPosition(position));
////                    switch (position) {
////
////                        case 1:
////                            etName.setText(selectedParty.getPresident());
////                            break;
////
////                        case 2:
////
////                            etName.setText(selectedParty.getDeputyPresident());
////                            break;
////
////                        case 3:
////
////                            etName.setText(selectedParty.getSecretaryGeneral());
////                            break;
////
////                        case 4:
////                            etName.setText(selectedParty.getFinancialOfficer());
////                            break;
////
////                        case 5:
////                            etName.setText(selectedParty.getConstitutionalAndLegalAffairs());
////                            break;
////
////                        case 6:
////                            etName.setText(selectedParty.getSportsOfficer());
////                            break;
////
////                        case 7:
////                            etName.setText(selectedParty.getPublicRelationsOfficer());
////                            break;
////
////                        case 8:
////                            etName.setText(selectedParty.getHealthAndWelfareOfficer());
////                            break;
////
////                        case 9:
////                            etName.setText(selectedParty.getProjectsAndCampaignOfficer());
////                            break;
////
////                        case 10:
////                            etName.setText(selectedParty.getStudentAffairs());
////                            break;
////
////                        case 11:
////                            etName.setText(selectedParty.getEquityAndDiversityOfficer());
////                            break;
////
////                        case 12:
////                            etName.setText(selectedParty.getTransformationOfficer());
////                            break;
////
////                        default:
////
////                            etName.setText(null);
////                            break;
////
////                    }
//
//                } else {
//
//                    hideViews(frmCandidateDetails);
//                    etName.setText(null);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));

        rvCandidates = findViewById(R.id.rvCandidates);
        rvCandidates.setHasFixedSize(true);


        etEmail = findViewById(R.id.etEmailRegCan);
        etFoundCandidateName = findViewById(R.id.etFoundCandidateNameRegCan);
        etFoundCandidateCourse = findViewById(R.id.etFoundCandidateCourseRegCan);
        ivScanCard = findViewById(R.id.ivScanCardRegCan);
        ivSearch = findViewById(R.id.ivSearchRegCan);
        ivResetParty = findViewById(R.id.ivResetParty);

        tvSelectedPortfolio = findViewById(R.id.tvSelectedPortfolio);
        frmParty = findViewById(R.id.frmPartyRegCan);
        frmCandidateDetails = findViewById(R.id.frmCandidateDetails);
        frmSearchEmail = findViewById(R.id.frmSearchEmailRegCan);
        frmFoundCandidate = findViewById(R.id.frmFoundCandidate);

        rgCandidatePartyRegCan = findViewById(R.id.rgCandidateRegCan);
        rbDASO = findViewById(R.id.rbDASORegCan);
        rbEFFSC = findViewById(R.id.rbEFFSCRegCan);
        rbSASCO = findViewById(R.id.rbSASCORegCan);
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

    public void onClick_ScanCard(View view) {

        scanStudentCard(this);
        showCustomToast(getApplicationContext(), toastView, "Scan Student Card");
    }

    public void onClick_SearchEmail(View view) {

        if (isEmailValid(etEmail)) {

            // TODO: 2019/10/24 If User is found populate fields
            showViews(frmFoundCandidate);

//            Backendless.Data.of(Party.class).find(selectQuery());
//            selectedParty.assignPortfolio();
//            etFoundCandidateName.setText();
//            etFoundCandidateCourse.setText();


        }

    }

    public void onClick_ResetPartySelection(View view) {

        resetForm();

    }

    private void resetForm() {

        uncheckRadioButton(rbEFFSC, rbDASO, rbSASCO);
        showViews(rbEFFSC, rbDASO, rbSASCO);
        clearFields(etEmail, etFoundCandidateName, etFoundCandidateCourse);
        etEmail.setError(null);
        hideViews(ivResetParty, frmParty, frmCandidateDetails, rvCandidates);
        selectedParty = null;
        selectedCandidate = "";
        selectedPosition = -1;
        selectedPortfolio = null;
        foundCandidate = null;

    }

    public void onClick_GoBack(View view) {

        showCandidateList();

    }

    private void showCandidateList() {

        hideViews(frmFoundCandidate);
        switchViews(rvCandidates, frmCandidateDetails);
        clearFields(etEmail, etFoundCandidateName, etFoundCandidateCourse);
        etEmail.setError(null);
    }

    public void onClick_AssignCandidate(View view) {

        // TODO: 2019/09/14 Get User string for found User if successful...

        showCustomToast(getApplicationContext(), toastView,
                etEmail.getText().toString().trim() + " assigned to Portfolio: "
                        + "CHECK!!");
        showCandidateList();


// TODO: 2019/10/24 REMOVE
        selectedParty.assignPortfolio(selectedPortfolio, etEmail.getText().toString().trim() );// getUserString(foundCandidate)


        switchViews(rvCandidates, frmCandidateDetails);

        candidates.set(selectedPosition, "Test, One");
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClicked(int index) {

        switchViews(frmCandidateDetails, rvCandidates);

        selectedCandidate = candidates.get(index);
        selectedPosition = index;

        String strCandidate = "Assign New Candidate to\n"
                + Portfolios[index] + " Portfolio:\n"
                + selectedCandidate.split(",")[0].trim() + " (current)";

        tvSelectedPortfolio.setText(strCandidate);
    }
}
