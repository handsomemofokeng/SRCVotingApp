package com.example.srcvotingapp.BL;

public class Vote {

    public Vote() {
        SelectedPresident = "Not selected";
        SelectedDeputyPresident = "Not selected";
        SelectedSecretaryGeneral = "Not selected";
        SelectedFinancialOfficer = "Not selected";
        SelectedConstitutionalAndLegalAffairs = "Not selected";
        SelectedSportsOfficer = "Not selected";
        SelectedPublicRelationsOfficer = "Not selected";
        SelectedHealthAndWelfareOfficer = "Not selected";
        SelectedProjectsAndCampaignOfficer = "Not selected";
        SelectedStudentAffairs = "Not selected";
        SelectedEquityAndDiversityOfficer = "Not selected";
        SelectedTransformationOfficer = "Not selected";
    }

    private String SelectedPresident;
    private String SelectedDeputyPresident;
    private String SelectedSecretaryGeneral;
    private String SelectedFinancialOfficer;
    private String SelectedConstitutionalAndLegalAffairs;
    private String SelectedSportsOfficer;
    private String SelectedPublicRelationsOfficer;
    private String SelectedHealthAndWelfareOfficer;
    private String SelectedProjectsAndCampaignOfficer;
    private String SelectedStudentAffairs;
    private String SelectedEquityAndDiversityOfficer;
    private String SelectedTransformationOfficer;

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
                "SelectedPresident='" + SelectedPresident + '\'' +
                ", SelectedDeputyPresident='" + SelectedDeputyPresident + '\'' +
                ", SelectedSecretaryGeneral='" + SelectedSecretaryGeneral + '\'' +
                ", SelectedFinancialOfficer='" + SelectedFinancialOfficer + '\'' +
                ", SelectedConstitutionalAndLegalAffairs='" + SelectedConstitutionalAndLegalAffairs + '\'' +
                ", SelectedSportsOfficer='" + SelectedSportsOfficer + '\'' +
                ", SelectedPublicRelationsOfficer='" + SelectedPublicRelationsOfficer + '\'' +
                ", SelectedHealthAndWelfareOfficer='" + SelectedHealthAndWelfareOfficer + '\'' +
                ", SelectedProjectsAndCampaignOfficer='" + SelectedProjectsAndCampaignOfficer + '\'' +
                ", SelectedStudentAffairs='" + SelectedStudentAffairs + '\'' +
                ", SelectedEquityAndDiversityOfficer='" + SelectedEquityAndDiversityOfficer + '\'' +
                ", SelectedTransformationOfficer='" + SelectedTransformationOfficer + '\'' +
                '}';
    }

    public boolean isVotesValid(){
        return this.toString().contains("Not selected");
    }
}