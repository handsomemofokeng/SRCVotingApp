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

import com.example.srcvotingapp.R;

import static com.example.srcvotingapp.ApplicationClass.getSelectedRadio;

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
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vote, container, false);

        tvSection = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvSection.setText(s);
            }
        });

        rgCandidateParty = root.findViewById(R.id.rgCandidate);
        rbDASO = root.findViewById(R.id.rbDASO);
        rbEFFSC = root.findViewById(R.id.rbEFFSC);
        rbSASCO = root.findViewById(R.id.rbSASCO);

        rgCandidateParty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // TODO: 2019/09/08 Create an Interface to link selected Radio to Attached Activity

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