package com.example.srcvotingapp.BL;

import weborb.service.MapToProperty;

import static com.example.srcvotingapp.ApplicationClass.Portfolios;

public class Party {

    public String objectId;

    @MapToProperty(property = "partyName")
    private String PartyName;
    @MapToProperty(property = "partyID")
    private String PartyID;
    @MapToProperty(property = "president")
    private String President;
    @MapToProperty(property = "deputyPresident")
    private String DeputyPresident;
    @MapToProperty(property = "secretaryGeneral")
    private String SecretaryGeneral;
    @MapToProperty(property = "financialOfficer")
    private String FinancialOfficer;
    @MapToProperty(property = "constitutionalAndLegalAffairs")
    private String ConstitutionalAndLegalAffairs;
    @MapToProperty(property = "sportsOfficer")
    private String SportsOfficer;
    @MapToProperty(property = "publicRelationsOfficer")
    private String PublicRelationsOfficer;
    @MapToProperty(property = "healthAndWelfareOfficer")
    private String HealthAndWelfareOfficer;
    @MapToProperty(property = "projectsAndCampaignOfficer")
    private String ProjectsAndCampaignOfficer;
    @MapToProperty(property = "studentAffairs")
    private String StudentAffairs;
    @MapToProperty(property = "equityAndDiversityOfficer")
    private String EquityAndDiversityOfficer;
    @MapToProperty(property = "transformationOfficer")
    private String TransformationOfficer;

    public Party() {
    }

    public Party(String partyName, String partyID) {

        PartyName = partyName;
        PartyID = partyID;

        setPresident("Not set, Not set");
        setDeputyPresident("Not set, Not set");
        setSecretaryGeneral("Not set, Not set");
        setFinancialOfficer("Not set, Not set");
        setConstitutionalAndLegalAffairs("Not set, Not set");
        setSportsOfficer("Not set, Not set");
        setPublicRelationsOfficer("Not set, Not set");
        setHealthAndWelfareOfficer("Not set, Not set");
        setProjectsAndCampaignOfficer("Not set, Not set");
        setStudentAffairs("Not set, Not set");
        setEquityAndDiversityOfficer("Not set, Not set");
        setTransformationOfficer("Not set, Not set");
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

//    public void setPartyID(String partyID) {
//        PartyID = partyID;
//    }

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
                "PartyName: " + PartyName + "\n" +
                "PartyID: " + PartyID + "\n" +
                "President: " + President + "\n" +
                "Deputy President: " + DeputyPresident + "\n" +
                "Secretary General: " + SecretaryGeneral + "\n" +
                "Financial Officer: " + FinancialOfficer + "\n" +
                "Constitutional And Legal Affairs: " + ConstitutionalAndLegalAffairs + "\n" +
                "Sports Officer: " + SportsOfficer + "\n" +
                "Public Relations Officer: " + PublicRelationsOfficer + "\n" +
                "Health And Welfare Officer: " + HealthAndWelfareOfficer + "\n" +
                "Projects And Campaign Officer: " + ProjectsAndCampaignOfficer + "\n" +
                "Student Affairs: " + StudentAffairs + "\n" +
                "Equity And Diversity Officer: " + EquityAndDiversityOfficer + "\n" +
                "Transformation Officer: " + TransformationOfficer;
    }

    public String getCandidateByPosition(int position) {
        String candidate = "Not set, Not set";
        switch (position) {

            case 0:
                candidate = getPresident();
                break;

            case 1:

                candidate = getDeputyPresident();
                break;

            case 2:

                candidate = getSecretaryGeneral();
                break;

            case 3:
                candidate = getFinancialOfficer();
                break;

            case 4:
                candidate = getConstitutionalAndLegalAffairs();
                break;

            case 5:
                candidate = getSportsOfficer();
                break;

            case 6:
                candidate = getPublicRelationsOfficer();
                break;

            case 7:
                candidate = getHealthAndWelfareOfficer();
                break;

            case 8:
                candidate = getProjectsAndCampaignOfficer();
                break;

            case 9:
                candidate = getStudentAffairs();
                break;

            case 10:
                candidate = getEquityAndDiversityOfficer();
                break;

            case 11:
                candidate = getTransformationOfficer();
                break;

        }

        return candidate;
    }

    public String getCandidateByPortfolio(String portfolio) {
        String candidate = "Not set, Not set";
        switch (portfolio.trim().toLowerCase()) {

            case "President":
                candidate = getPresident();
                break;

            case "Deputy President":

                candidate = getDeputyPresident();
                break;

            case "Secretary General":

                candidate = getSecretaryGeneral();
                break;

            case "Financial Officer":
                candidate = getFinancialOfficer();
                break;

            case "Constitutional And Legal Affairs":
                candidate = getConstitutionalAndLegalAffairs();
                break;

            case "Sports Officer":
                candidate = getSportsOfficer();
                break;

            case "Public Relations Officer":
                candidate = getPublicRelationsOfficer();
                break;

            case "Health And Welfare Officer":
                candidate = getHealthAndWelfareOfficer();
                break;

            case "Projects And Campaign Officer":
                candidate = getProjectsAndCampaignOfficer();
                break;

            case "Student Affairs":
                candidate = getStudentAffairs();
                break;

            case "Equity And Diversity Officer":
                candidate = getEquityAndDiversityOfficer();
                break;

            case "Transformation Officer":
                candidate = getTransformationOfficer();
                break;

        }

        return candidate;
    }

}
