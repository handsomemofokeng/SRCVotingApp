package com.example.srcvotingapp.ui.vote;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.srcvotingapp.BL.Party;
import com.example.srcvotingapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import static com.example.srcvotingapp.ApplicationClass.disableViews;
import static com.example.srcvotingapp.ApplicationClass.enableViews;
import static com.example.srcvotingapp.ApplicationClass.getSelectedRadio;
import static com.example.srcvotingapp.ApplicationClass.progressDialog;
import static com.example.srcvotingapp.ApplicationClass.selectAllQuery;
import static com.example.srcvotingapp.ApplicationClass.showProgressDialog;
import static com.example.srcvotingapp.VoteActivity.partyList;

/**
 * A placeholder fragment containing a simple view.
 */
public class VoteFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    RadioButton rbEFFSC, rbDASO, rbSASCO;
    RadioGroup rgCandidateParty;
    TextView tvSection;


    private PageViewModel pageViewModel;

    private SetCandidateListener setCandidateListener;

    public static VoteFragment newInstance(int index) {
        VoteFragment fragment = new VoteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (partyList != null) {
            if (!partyList.isEmpty()) {
                rbDASO.setText(partyList.get(0).getCandidateByPosition(getArguments().getInt(ARG_SECTION_NUMBER) - 1));
                rbEFFSC.setText(partyList.get(1).getCandidateByPosition(getArguments().getInt(ARG_SECTION_NUMBER)- 1));
                rbSASCO.setText(partyList.get(2).getCandidateByPosition(getArguments().getInt(ARG_SECTION_NUMBER)- 1));
            }
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vote, container, false);

        rgCandidateParty = root.findViewById(R.id.rgCandidate);

        rbDASO = root.findViewById(R.id.rbDASO);
        rbEFFSC = root.findViewById(R.id.rbEFFSC);
        rbSASCO = root.findViewById(R.id.rbSASCO);
        tvSection = root.findViewById(R.id.section_label);

        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvSection.setText(s);

//                if (partyList != null) {
//                    if (!partyList.isEmpty()) {
//                        rbDASO.setText(partyList.get(0).getCandidateByPosition(getArguments().getInt(ARG_SECTION_NUMBER) - 1));
//                        rbEFFSC.setText(partyList.get(1).getCandidateByPosition(getArguments().getInt(ARG_SECTION_NUMBER)- 1));
//                        rbSASCO.setText(partyList.get(2).getCandidateByPosition(getArguments().getInt(ARG_SECTION_NUMBER)- 1));
//                    }
//                }

            }
        });


        rgCandidateParty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                String selectedPartyID = "";

                switch (checkedId) {
                    case R.id.rbDASO:
                        selectedPartyID = "DASO";
                        break;

                    case R.id.rbEFFSC:
                        selectedPartyID = "EFFSC";
                        break;

                    case R.id.rbSASCO:
                        selectedPartyID = "SASCO";
                        break;
                }


//                if (partyList != null) {
//                    if (!partyList.isEmpty()) {
//                        rbDASO.setText(partyList.get(0).getCandidateByPosition(getArguments().getInt(ARG_SECTION_NUMBER) - 1));
//                        rbEFFSC.setText(partyList.get(1).getCandidateByPosition(getArguments().getInt(ARG_SECTION_NUMBER)- 1));
//                        rbSASCO.setText(partyList.get(2).getCandidateByPosition(getArguments().getInt(ARG_SECTION_NUMBER)- 1));
//                    }
//                }

                setCandidateListener.onSetCandidate(selectedPartyID,
                        tvSection.getText().toString().trim());
            }
        });
        return root;
    }

    public interface SetCandidateListener {
        void onSetCandidate(String candidatePartyID, String portfolio);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            setCandidateListener = (VoteFragment.SetCandidateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement interface"
                    + " SetCandidateListener");
        }
    }

}