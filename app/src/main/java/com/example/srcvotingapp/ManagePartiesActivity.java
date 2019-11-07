package com.example.srcvotingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.srcvotingapp.BL.Party;

import java.util.ArrayList;
import java.util.List;

import static com.example.srcvotingapp.ApplicationClass.buildAlertDialog;
import static com.example.srcvotingapp.ApplicationClass.deselectCheckBoxes;
import static com.example.srcvotingapp.ApplicationClass.getUserFullName;
import static com.example.srcvotingapp.ApplicationClass.hideViews;
import static com.example.srcvotingapp.ApplicationClass.isCheckBoxSelected;
import static com.example.srcvotingapp.ApplicationClass.progressDialog;
import static com.example.srcvotingapp.ApplicationClass.selectAllQuery;
import static com.example.srcvotingapp.ApplicationClass.selectCheckBoxes;
import static com.example.srcvotingapp.ApplicationClass.sessionUser;
import static com.example.srcvotingapp.ApplicationClass.setupActionBar;
import static com.example.srcvotingapp.ApplicationClass.showCustomToast;
import static com.example.srcvotingapp.ApplicationClass.showProgressDialog;
import static com.example.srcvotingapp.ApplicationClass.showViews;

public class ManagePartiesActivity extends AppCompatActivity {

    View toastView;
    CheckBox chkSelectAll, chkDASO, chkSASCO, chkEFFSC;

    FloatingActionButton fabResetParty, fabAddCandidate;

    List<Party> partyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_parties);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setupActionBar(getSupportActionBar(), "Manage Parties",
                    getUserFullName(sessionUser));

        initViews();

        Backendless.Data.of(Party.class).find(selectAllQuery("partyName"),
                new AsyncCallback<List<Party>>() {
                    @Override
                    public void handleResponse(List<Party> response) {
                        partyList.clear();
                        partyList.addAll(response);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });

        hideViews(fabResetParty, fabAddCandidate);

        if (isCheckBoxSelected(chkSelectAll, chkDASO, chkSASCO, chkEFFSC)) {
            showViews(fabResetParty, fabAddCandidate);
        }

        partyList = new ArrayList<>();

        chkSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectCheckBoxes(chkSelectAll, chkDASO, chkSASCO, chkEFFSC);
                    showViews(fabResetParty, fabAddCandidate);
                } else {
                    chkSelectAll.setText(getString(R.string.select_all));
                    if (!isCheckBoxSelected(chkDASO, chkSASCO, chkEFFSC))
                        deselectCheckBoxes(chkSelectAll, chkDASO, chkSASCO, chkEFFSC);
                }

            }
        });
        chkDASO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lookupCheckedBoxes(isChecked);

            }
        });
        chkEFFSC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lookupCheckedBoxes(isChecked);

            }
        });
        chkSASCO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lookupCheckedBoxes(isChecked);
            }
        });

    }

    private void lookupCheckedBoxes(boolean isChecked) {
        if (isChecked) {
            showViews(fabResetParty, fabAddCandidate);
        } else {
            chkSelectAll.setChecked(false);
            chkSelectAll.setText(getString(R.string.select_all));
            if (!isCheckBoxSelected(chkDASO, chkSASCO, chkEFFSC)) {
                hideViews(fabResetParty, fabAddCandidate);
            }
        }
    }

    private void initViews() {

        toastView = getLayoutInflater().inflate(R.layout.custom_toast, (
                ViewGroup) findViewById(R.id.toast_layout));

        chkSelectAll = findViewById(R.id.chkSelectAll);
        chkDASO = findViewById(R.id.chkDASO);
        chkSASCO = findViewById(R.id.chkSASCO);
        chkEFFSC = findViewById(R.id.chkEFFSC);

        fabAddCandidate = findViewById(R.id.fabAddCandidate);
        fabResetParty = findViewById(R.id.fabResetParty);
    }

    public void onClick_AddCandidate(View view) {
        startActivity(new Intent(ManagePartiesActivity.this, AddCandidateActivity.class));
    }

    public void onClick_GoHome(View view) {
        finish();
    }

//    public void onClick_RegisterParty(View view) {
//
//        view.setVisibility(View.GONE);
//
//        List<Party> partyList = new ArrayList<>();
//
//        partyList.add(new Party("Economic Freedom Fighters Students' Command", "EFFSC"));
//
//        partyList.add(new Party("Democratic Alliance Student Organisation", "DASO"));
//
//        partyList.add(new Party("South African Student Congress", "SASCO"));
//
//        showProgressDialog(ManagePartiesActivity.this, "Registering Parties",
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
//
////        if (selectedParty != null) {
////
////            queryParty = selectQuery(PARTY_ID, selectedParty.getPartyID(), PARTY_ID);
////
////            layoutManager = new LinearLayoutManager(AddCandidateActivity.this,
////                    LinearLayout.VERTICAL, false);
////
////            rvCandidates.setLayoutManager(layoutManager);
////
////            candidates = new ArrayList<>();
////
////            candidates.add(selectedParty.getPresident());
////            candidates.add(selectedParty.getDeputyPresident());
////            candidates.add(selectedParty.getSecretaryGeneral());
////            candidates.add(selectedParty.getFinancialOfficer());
////            candidates.add(selectedParty.getConstitutionalAndLegalAffairs());
////            candidates.add(selectedParty.getSportsOfficer());
////            candidates.add(selectedParty.getPublicRelationsOfficer());
////            candidates.add(selectedParty.getHealthAndWelfareOfficer());
////            candidates.add(selectedParty.getProjectsAndCampaignOfficer());
////            candidates.add(selectedParty.getStudentAffairs());
////            candidates.add(selectedParty.getEquityAndDiversityOfficer());
////            candidates.add(selectedParty.getTransformationOfficer());
////
////            myAdapter = new PartyAdapter(AddCandidateActivity.this, candidates);
////            rvCandidates.setAdapter(myAdapter);
////
//////                    tvPartyDetails.setText(selectedParty.toString());
////
////        }
//
//    }

    public void onClick_ResetParty(View view) {


        AlertDialog.Builder builder = buildAlertDialog(ManagePartiesActivity.this,
                "Reset Party", "Restore selected Parties to their default values?" +
                        "\n\nThis cannot be undone!");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if (chkEFFSC.isChecked())
                    partyList.add(new Party("Economic Freedom Fighters Students' Command",
                            "EFFSC"));
                if (chkDASO.isChecked())
                    partyList.add(new Party("Democratic Alliance Student Organisation",
                            "DASO"));
                if (chkSASCO.isChecked())
                    partyList.add(new Party("South African Student Congress",
                            "SASCO"));

                if (!partyList.isEmpty()) {

                    for (int i = 0; i < partyList.size(); i++) {

                        Party party = partyList.get(i);

                        showProgressDialog(ManagePartiesActivity.this, "Restore Parties",
                                "Restoring " + party.getPartyName() + ", please wait...",
                                true);

                        Backendless.Persistence.save(party, new AsyncCallback<Party>() {
                            @Override
                            public void handleResponse(Party response) {
                                progressDialog.dismiss();
                                showCustomToast(ManagePartiesActivity.this, toastView,
                                        response.getPartyName() + " restored successfully");

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                progressDialog.dismiss();
                                showCustomToast(ManagePartiesActivity.this, toastView,
                                        fault.getMessage());
                            }
                        });
                        progressDialog.dismiss();

                    }
                } else {
                    showCustomToast(ManagePartiesActivity.this, toastView,
                            "No Party selected");
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

//    private void showMessageDialog(String title, String message) {
//        AlertDialog.Builder builder = buildAlertDialog(
//                ManagePartiesActivity.this, title, message);
//        builder.setPositiveButton("OK",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//        builder.create().show();
//    }
}