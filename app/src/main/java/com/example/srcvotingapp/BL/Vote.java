package com.example.srcvotingapp.BL;

import weborb.service.MapToProperty;

import static com.example.srcvotingapp.ApplicationClass.Portfolios;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_ConstitutionalAndLegalAffairs;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_DeputyPresident;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_EquityAndDiversityOfficer;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_FinancialOfficer;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_HealthAndWelfareOfficer;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_President;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_ProjectsAndCampaignOfficer;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_PublicRelationsOfficer;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_SecretaryGeneral;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_SportsOfficer;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_StudentAffairs;
import static com.example.srcvotingapp.ApplicationClass.SELECTED_TransformationOfficer;

public class Vote {

    private String objectId;

    @MapToProperty(property = SELECTED_President)
    private String SelectedPresident;

    @MapToProperty(property = SELECTED_DeputyPresident)
    private String SelectedDeputyPresident;

    @MapToProperty(property = SELECTED_SecretaryGeneral)
    private String SelectedSecretaryGeneral;

    @MapToProperty(property = SELECTED_FinancialOfficer)
    private String SelectedFinancialOfficer;

    @MapToProperty(property = SELECTED_ConstitutionalAndLegalAffairs)
    private String SelectedConstitutionalAndLegalAffairs;

    @MapToProperty(property = SELECTED_SportsOfficer)
    private String SelectedSportsOfficer;

    @MapToProperty(property = SELECTED_PublicRelationsOfficer)
    private String SelectedPublicRelationsOfficer;

    @MapToProperty(property = SELECTED_HealthAndWelfareOfficer)
    private String SelectedHealthAndWelfareOfficer;

    @MapToProperty(property = SELECTED_ProjectsAndCampaignOfficer)
    private String SelectedProjectsAndCampaignOfficer;

    @MapToProperty(property = SELECTED_StudentAffairs)
    private String SelectedStudentAffairs;

    @MapToProperty(property = SELECTED_EquityAndDiversityOfficer)
    private String SelectedEquityAndDiversityOfficer;

    @MapToProperty(property = SELECTED_TransformationOfficer)
    private String SelectedTransformationOfficer;

    public Vote() {
    }

//    private Boolean votesValid;
//
//    public Boolean getVotesValid() {
//        return votesValid;
//    }
//
//    public void setVotesValid(Boolean votesValid) {
//        this.votesValid = votesValid;
//    }

    public String getSelectedPresident() {
        return SelectedPresident;
    }

    public void setSelectedPresident(String selectedPresident) {
        SelectedPresident = selectedPresident;
    }

    public String getSelectedDeputyPresident() {
        return SelectedDeputyPresident;
    }

    public void setSelectedDeputyPresident(String selectedDeputyPresident) {
        SelectedDeputyPresident = selectedDeputyPresident;
    }

    public String getSelectedSecretaryGeneral() {
        return SelectedSecretaryGeneral;
    }

    public void setSelectedSecretaryGeneral(String selectedSecretaryGeneral) {
        SelectedSecretaryGeneral = selectedSecretaryGeneral;
    }

    public String getSelectedFinancialOfficer() {
        return SelectedFinancialOfficer;
    }

    public void setSelectedFinancialOfficer(String selectedFinancialOfficer) {
        SelectedFinancialOfficer = selectedFinancialOfficer;
    }

    public String getSelectedConstitutionalAndLegalAffairs() {
        return SelectedConstitutionalAndLegalAffairs;
    }

    public void setSelectedConstitutionalAndLegalAffairs(String selectedConstitutionalAndLegalAffairs) {
        SelectedConstitutionalAndLegalAffairs = selectedConstitutionalAndLegalAffairs;
    }

    public String getSelectedSportsOfficer() {
        return SelectedSportsOfficer;
    }

    public void setSelectedSportsOfficer(String selectedSportsOfficer) {
        SelectedSportsOfficer = selectedSportsOfficer;
    }

    public String getSelectedPublicRelationsOfficer() {
        return SelectedPublicRelationsOfficer;
    }

    public void setSelectedPublicRelationsOfficer(String selectedPublicRelationsOfficer) {
        SelectedPublicRelationsOfficer = selectedPublicRelationsOfficer;
    }

    public String getSelectedHealthAndWelfareOfficer() {
        return SelectedHealthAndWelfareOfficer;
    }

    public void setSelectedHealthAndWelfareOfficer(String selectedHealthAndWelfareOfficer) {
        SelectedHealthAndWelfareOfficer = selectedHealthAndWelfareOfficer;
    }

    public String getSelectedProjectsAndCampaignOfficer() {
        return SelectedProjectsAndCampaignOfficer;
    }

    public void setSelectedProjectsAndCampaignOfficer(String selectedProjectsAndCampaignOfficer) {
        SelectedProjectsAndCampaignOfficer = selectedProjectsAndCampaignOfficer;
    }

    public String getSelectedStudentAffairs() {
        return SelectedStudentAffairs;
    }

    public void setSelectedStudentAffairs(String selectedStudentAffairs) {
        SelectedStudentAffairs = selectedStudentAffairs;
    }

    public String getSelectedEquityAndDiversityOfficer() {
        return SelectedEquityAndDiversityOfficer;
    }

    public void setSelectedEquityAndDiversityOfficer(String selectedEquityAndDiversityOfficer) {
        SelectedEquityAndDiversityOfficer = selectedEquityAndDiversityOfficer;
    }

    public String getSelectedTransformationOfficer() {
        return SelectedTransformationOfficer;
    }

    public void setSelectedTransformationOfficer(String selectedTransformationOfficer) {
        SelectedTransformationOfficer = selectedTransformationOfficer;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "SelectedPresident='" + getSelectedPresident() + '\'' +
                ", SelectedDeputyPresident='" + getSelectedDeputyPresident() + '\'' +
                ", SelectedSecretaryGeneral='" + getSelectedSecretaryGeneral() + '\'' +
                ", SelectedFinancialOfficer='" + getSelectedFinancialOfficer() + '\'' +
                ", SelectedConstitutionalAndLegalAffairs='" + getSelectedConstitutionalAndLegalAffairs() + '\'' +
                ", SelectedSportsOfficer='" + getSelectedSportsOfficer() + '\'' +
                ", SelectedPublicRelationsOfficer='" + getSelectedPublicRelationsOfficer() + '\'' +
                ", SelectedHealthAndWelfareOfficer='" + getSelectedHealthAndWelfareOfficer() + '\'' +
                ", SelectedProjectsAndCampaignOfficer='" + getSelectedProjectsAndCampaignOfficer() + '\'' +
                ", SelectedStudentAffairs='" + getSelectedStudentAffairs() + '\'' +
                ", SelectedEquityAndDiversityOfficer='" + getSelectedEquityAndDiversityOfficer() + '\'' +
                ", SelectedTransformationOfficer='" + getSelectedTransformationOfficer() + '\'' +
                '}';
    }

    public boolean isVotesValid() {
        return !this.toString().contains("Not selected");
    }

//    public Vote(String selectedPresident, String selectedDeputyPresident,
//                String selectedSecretaryGeneral, String selectedFinancialOfficer,
//                String selectedConstitutionalAndLegalAffairs, String selectedSportsOfficer,
//                String selectedPublicRelationsOfficer, String selectedHealthAndWelfareOfficer,
//                String selectedProjectsAndCampaignOfficer, String selectedStudentAffairs,
//                String selectedEquityAndDiversityOfficer, String selectedTransformationOfficer) {
//
//        SelectedPresident = selectedPresident;
//        SelectedDeputyPresident = selectedDeputyPresident;
//        SelectedSecretaryGeneral = selectedSecretaryGeneral;
//        SelectedFinancialOfficer = selectedFinancialOfficer;
//        SelectedConstitutionalAndLegalAffairs = selectedConstitutionalAndLegalAffairs;
//        SelectedSportsOfficer = selectedSportsOfficer;
//        SelectedPublicRelationsOfficer = selectedPublicRelationsOfficer;
//        SelectedHealthAndWelfareOfficer = selectedHealthAndWelfareOfficer;
//        SelectedProjectsAndCampaignOfficer = selectedProjectsAndCampaignOfficer;
//        SelectedStudentAffairs = selectedStudentAffairs;
//        SelectedEquityAndDiversityOfficer = selectedEquityAndDiversityOfficer;
//        SelectedTransformationOfficer = selectedTransformationOfficer;
//    }

    public void assignVotes(String selectedPortfolio, String selectedPartyID) {

        if (selectedPortfolio.equals(Portfolios[0]))
            setSelectedPresident(selectedPartyID);
        if (selectedPortfolio.equals(Portfolios[1]))
            setSelectedDeputyPresident(selectedPartyID);
        if (selectedPortfolio.equals(Portfolios[2]))
            setSelectedSecretaryGeneral(selectedPartyID);
        if (selectedPortfolio.equals(Portfolios[3]))
            setSelectedFinancialOfficer(selectedPartyID);
        if (selectedPortfolio.equals(Portfolios[4]))
            setSelectedConstitutionalAndLegalAffairs(selectedPartyID);
        if (selectedPortfolio.equals(Portfolios[5]))
            setSelectedSportsOfficer(selectedPartyID);
        if (selectedPortfolio.equals(Portfolios[6]))
            setSelectedPublicRelationsOfficer(selectedPartyID);
        if (selectedPortfolio.equals(Portfolios[7]))
            setSelectedHealthAndWelfareOfficer(selectedPartyID);
        if (selectedPortfolio.equals(Portfolios[8]))
            setSelectedProjectsAndCampaignOfficer(selectedPartyID);
        if (selectedPortfolio.equals(Portfolios[9]))
            setSelectedStudentAffairs(selectedPartyID);
        if (selectedPortfolio.equals(Portfolios[10]))
            setSelectedEquityAndDiversityOfficer(selectedPartyID);
        if (selectedPortfolio.equals(Portfolios[11]))
            setSelectedTransformationOfficer(selectedPartyID);
    }

    public String getCandidateByPortfolio(String selectedPortfolio) {
        String candidate = "Not selected";
        if (selectedPortfolio.equals(Portfolios[0]))
            candidate = getSelectedPresident();
        if (selectedPortfolio.equals(Portfolios[1]))
            candidate = getSelectedDeputyPresident();
        if (selectedPortfolio.equals(Portfolios[2]))
            candidate = getSelectedSecretaryGeneral();
        if (selectedPortfolio.equals(Portfolios[3]))
            candidate = getSelectedFinancialOfficer();
        if (selectedPortfolio.equals(Portfolios[4]))
            candidate = getSelectedConstitutionalAndLegalAffairs();
        if (selectedPortfolio.equals(Portfolios[5]))
            candidate = getSelectedSportsOfficer();
        if (selectedPortfolio.equals(Portfolios[6]))
            candidate = getSelectedPublicRelationsOfficer();
        if (selectedPortfolio.equals(Portfolios[7]))
            candidate = getSelectedHealthAndWelfareOfficer();
        if (selectedPortfolio.equals(Portfolios[8]))
            candidate = getSelectedProjectsAndCampaignOfficer();
        if (selectedPortfolio.equals(Portfolios[9]))
            candidate = getSelectedStudentAffairs();
        if (selectedPortfolio.equals(Portfolios[10]))
            candidate = getSelectedEquityAndDiversityOfficer();
        if (selectedPortfolio.equals(Portfolios[11]))
            candidate = getSelectedTransformationOfficer();

        return candidate;
    }

}