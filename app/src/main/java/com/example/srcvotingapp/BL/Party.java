package com.example.srcvotingapp.BL;

import static com.example.srcvotingapp.ApplicationClass.Portfolios;

public class Party {

    private String PartyName;
    private String PartyID;
    private String President;
    private String DeputyPresident;
    private String SecretaryGeneral;
    private String FinancialOfficer;
    private String ConstitutionalAndLegalAffairs;
    private String SportsOfficer;
    private String PublicRelationsOfficer;
    private String HealthAndWelfareOfficer;
    private String ProjectsAndCampaignOfficer;
    private String StudentAffairs;
    private String EquityAndDiversityOfficer;
    private String TransformationOfficer;

    public Party(String partyName, String partyID) {
        PartyName = partyName;
        PartyID = partyID;

        setPresident("Not set");
        setDeputyPresident("Not set");
        setSecretaryGeneral("Not set");
        setFinancialOfficer("Not set");
        setConstitutionalAndLegalAffairs("Not set");
        setSportsOfficer("Not set");
        setPublicRelationsOfficer("Not set");
        setHealthAndWelfareOfficer("Not set");
        setProjectsAndCampaignOfficer("Not set");
        setStudentAffairs("Not set");
        setEquityAndDiversityOfficer("Not set");
        setTransformationOfficer("Not set");
    }

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }

    public String getPartyID() {
        return PartyID;
    }

    public void setPartyID(String partyID) {
        PartyID = partyID;
    }

    public String getPresident() {
        return President;
    }

    public void setPresident(String president) {
        President = president;
    }

    public String getDeputyPresident() {
        return DeputyPresident;
    }

    public void setDeputyPresident(String deputyPresident) {
        DeputyPresident = deputyPresident;
    }

    public String getSecretaryGeneral() {
        return SecretaryGeneral;
    }

    public void setSecretaryGeneral(String secretaryGeneral) {
        SecretaryGeneral = secretaryGeneral;
    }

    public String getFinancialOfficer() {
        return FinancialOfficer;
    }

    public void setFinancialOfficer(String financialOfficer) {
        FinancialOfficer = financialOfficer;
    }

    public String getConstitutionalAndLegalAffairs() {
        return ConstitutionalAndLegalAffairs;
    }

    public void setConstitutionalAndLegalAffairs(String constitutionalAndLegalAffairs) {
        ConstitutionalAndLegalAffairs = constitutionalAndLegalAffairs;
    }

    public String getSportsOfficer() {
        return SportsOfficer;
    }

    public void setSportsOfficer(String sportsOfficer) {
        SportsOfficer = sportsOfficer;
    }

    public String getPublicRelationsOfficer() {
        return PublicRelationsOfficer;
    }

    public void setPublicRelationsOfficer(String publicRelationsOfficer) {
        PublicRelationsOfficer = publicRelationsOfficer;
    }

    public String getHealthAndWelfareOfficer() {
        return HealthAndWelfareOfficer;
    }

    public void setHealthAndWelfareOfficer(String healthAndWelfareOfficer) {
        HealthAndWelfareOfficer = healthAndWelfareOfficer;
    }

    public String getProjectsAndCampaignOfficer() {
        return ProjectsAndCampaignOfficer;
    }

    public void setProjectsAndCampaignOfficer(String projectsAndCampaignOfficer) {
        ProjectsAndCampaignOfficer = projectsAndCampaignOfficer;
    }

    public String getStudentAffairs() {
        return StudentAffairs;
    }

    public void setStudentAffairs(String studentAffairs) {
        StudentAffairs = studentAffairs;
    }

    public String getEquityAndDiversityOfficer() {
        return EquityAndDiversityOfficer;
    }

    public void setEquityAndDiversityOfficer(String equityAndDiversityOfficer) {
        EquityAndDiversityOfficer = equityAndDiversityOfficer;
    }

    public String getTransformationOfficer() {
        return TransformationOfficer;
    }

    public void setTransformationOfficer(String transformationOfficer) {
        TransformationOfficer = transformationOfficer;
    }

    public void assignPortfolio(String selectedPortfolio, String selectedCandidate) {

        if (selectedPortfolio.equals(Portfolios[0]))
            setPresident(selectedCandidate);
        if (selectedPortfolio.equals(Portfolios[1]))
            setDeputyPresident(selectedCandidate);
        if (selectedPortfolio.equals(Portfolios[2]))
            setSecretaryGeneral(selectedCandidate);
        if (selectedPortfolio.equals(Portfolios[3]))
            setFinancialOfficer(selectedCandidate);
        if (selectedPortfolio.equals(Portfolios[4]))
            setConstitutionalAndLegalAffairs(selectedCandidate);
        if (selectedPortfolio.equals(Portfolios[5]))
            setSportsOfficer(selectedCandidate);
        if (selectedPortfolio.equals(Portfolios[6]))
            setPublicRelationsOfficer(selectedCandidate);
        if (selectedPortfolio.equals(Portfolios[7]))
            setHealthAndWelfareOfficer(selectedCandidate);
        if (selectedPortfolio.equals(Portfolios[8]))
            setProjectsAndCampaignOfficer(selectedCandidate);
        if (selectedPortfolio.equals(Portfolios[9]))
            setStudentAffairs(selectedCandidate);
        if (selectedPortfolio.equals(Portfolios[10]))
            setEquityAndDiversityOfficer(selectedCandidate);
        if (selectedPortfolio.equals(Portfolios[11]))
            setTransformationOfficer(selectedCandidate);
    }

    @Override
    public String toString() {

        return "Party Details" + "\n" +
                "\tPartyName: " + PartyName  + "\n" +
                "\tPartyID: " + PartyID  + "\n" +
                "\tPresident: " + President  + "\n" +
                "\tDeputy President: " + DeputyPresident  + "\n" +
                "\tSecretary General: " + SecretaryGeneral  + "\n" +
                "\tFinancial Officer: " + FinancialOfficer  + "\n" +
                "\tConstitutional And Legal Affairs: " + ConstitutionalAndLegalAffairs  + "\n" +
                "\tSports Officer: " + SportsOfficer  + "\n" +
                "\tPublic Relations Officer: " + PublicRelationsOfficer  + "\n" +
                "\tHealthAnd Welfare Officer: " + HealthAndWelfareOfficer  + "\n" +
                "\tProjects And Campaign Officer: " + ProjectsAndCampaignOfficer  + "\n" +
                "\tStudent Affairs: " + StudentAffairs  + "\n" +
                "\tEquity And Diversity Officer: " + EquityAndDiversityOfficer  + "\n" +
                "\tTransformation Officer: " + TransformationOfficer;
    }

}