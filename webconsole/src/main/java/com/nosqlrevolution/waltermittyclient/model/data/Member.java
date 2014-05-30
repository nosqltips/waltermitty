package com.nosqlrevolution.waltermittyclient.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Member {
    private int newMemberID;
    private String state;
    private String zip;
    private String gender;
    private String birthYear;
    private Integer birthYearNum;
    private String birthDecade;
    private Integer age;
    private Timestamp hsaEffectiveDate;

    private int numberOfDependents;
    private ArrayList<Dependent> dependents;

    private int numberOfContributionsAndPayments;
    private int numberOfEmployeeContributions;
    private int numberOfEmployerContributions;
    private int numberofPayments;
    private Float totalEmployeeContributions;  // total dollar amount of employee contributions
    private Float totalEmployerContributions;  // total dollar amount of EMPLOYER contributions
    private Float totalPayments;  // total dollar amount of payments
    private Float totalContributionsAndPayments;
    private ArrayList<ContributionsAndPayments> contributionsAndPayments;
    private ArrayList<ContributionsAndPayments> memberContributions;
    private ArrayList<ContributionsAndPayments> memberPayments;
    private ArrayList<ContributionsAndPayments> companyContributions;

    private int numberOfBalances;
    private Float totalOfBalances;
    private ArrayList<Balance> balances;

    private int numberOfClaims;
    private Float totalClaimsRepricedAmount;
    private Float totalClaimsPatientResponsibilityAmount;
    private ArrayList<Claim> claims;

    private List<String> cptCodesAll = new ArrayList<>();
    private Set<String> cptCodesUnique = new HashSet<>();

    @JsonProperty("newMemberID")
    public int getNewMemberID() {
        return newMemberID;
    }

    @JsonProperty("newMemberID")
    public void setNewMemberID(int newMemberID) {
        this.newMemberID = newMemberID;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("zip")
    public String getZip() {
        return zip;
    }

    @JsonProperty("zip")
    public void setZip(String zip) {
        this.zip = zip;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("birthYear")
    public String getBirthYear() {
        return birthYear;
    }

    @JsonProperty("birthYear")
    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    @JsonProperty("birthYearNum")
    public Integer getBirthYearNum() {
        return birthYearNum;
    }

    @JsonProperty("birthYearNum")
    public void setBirthYearNum(Integer birthYearNum) {
        this.birthYearNum = birthYearNum;
    }

    @JsonProperty("birthDecade")
    public String getBirthDecade() {
        return birthDecade;
    }

    @JsonProperty("birthDecade")
    public void setBirthDecade(String birthDecade) {
        this.birthDecade = birthDecade;
    }

    @JsonProperty("age")
    public Integer getAge() {
        return age;
    }

    @JsonProperty("age")
    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonProperty("hsaEffectiveDate")
    public Timestamp getHsaEffectiveDate() {
        return hsaEffectiveDate;
    }

    @JsonProperty("hsaEffectiveDate")
    public void setHsaEffectiveDate(Timestamp hsaEffectiveDate) {
        this.hsaEffectiveDate = hsaEffectiveDate;
    }

    @JsonProperty("dependents")
    public ArrayList<Dependent> getDependents() {
        return dependents;
    }

    @JsonProperty("dependents")
    public void setDependents(ArrayList<Dependent> dependents) {
        this.dependents = dependents;

        if (dependents != null)
        {
            setNumberOfDependents(dependents.size());
        }
    }

    @JsonProperty("contributionsAndPayments")
    public ArrayList<ContributionsAndPayments> getContributionsAndPayments() {
        return contributionsAndPayments;
    }

    @JsonProperty("contributionsAndPayments")
    public void setContributionsAndPayments(ArrayList<ContributionsAndPayments> contributionsAndPayments) {
        this.contributionsAndPayments = contributionsAndPayments;

        if (contributionsAndPayments != null)
        {
            setContributionInfo(contributionsAndPayments);
        }

    }

    @JsonProperty("memberContributions")
    public ArrayList<ContributionsAndPayments> getMemberContributions() {
        return memberContributions;
    }

    @JsonProperty("memberContributions")
    public void setMemberContributions(ArrayList<ContributionsAndPayments> memberContributions) {
        this.memberContributions = memberContributions;
    }

    @JsonProperty("memberPayments")
    public ArrayList<ContributionsAndPayments> getMemberPayments() {
        return memberPayments;
    }

    @JsonProperty("memberPayments")
    public void setMemberPayments(ArrayList<ContributionsAndPayments> memberPayments) {
        this.memberPayments = memberPayments;
    }

    @JsonProperty("companyContributions")
    public ArrayList<ContributionsAndPayments> getCompanyContributions() {
        return companyContributions;
    }

    @JsonProperty("companyContributions")
    public void setCompanyContributions(ArrayList<ContributionsAndPayments> companyContributions) {
        this.companyContributions = companyContributions;
    }

    @JsonProperty("balances")
    public ArrayList<Balance> getBalances() {
        return balances;
    }

    @JsonProperty("balances")
    public void setBalances(ArrayList<Balance> balances)
    {
        this.balances = balances;

        setBalancesInfo(balances);
    }

    @JsonIgnore
    private void setBalancesInfo(ArrayList<Balance> balances)
    {
        if (balances != null)
        {
            setNumberOfBalances(balances.size());

            Float totalOfBalances = new Float(0);
            for (Balance balance : balances)
            {
                if (balance.getCachedBalance() != null)
                {
                    totalOfBalances = totalOfBalances += balance.getCachedBalance();
                }
            }

            setTotalOfBalances(totalOfBalances);
        }

    }

    @JsonProperty("claims")
    public ArrayList<Claim> getClaims() {
        return claims;
    }

    @JsonProperty("claims")
    public void setClaims(ArrayList<Claim> claims)
    {
        this.claims = claims;

        setClaimsInfo(claims);
    }

    @JsonIgnore
    private void setClaimsInfo(ArrayList<Claim> claims)
    {
        if (claims != null)
        {
            setNumberOfClaims(claims.size());

            Float totalClaimsRepricedAmount = new Float(0);
            Float totalClaimsPatientResponsibilityAmount = new Float(0);
            for (Claim claim : claims)
            {
                if (claim.getRepricedAmount() != null)
                {
                    totalClaimsRepricedAmount = totalClaimsRepricedAmount += claim.getRepricedAmount();
                }

                if (claim.getPatientResponsibilityAmount() != null)
                {
                    totalClaimsPatientResponsibilityAmount = totalClaimsPatientResponsibilityAmount+= claim.getPatientResponsibilityAmount();
                }

            }
            setTotalClaimsRepricedAmount(totalClaimsRepricedAmount);
            setTotalClaimsPatientResponsibilityAmount(totalClaimsPatientResponsibilityAmount);
        }
    }

    @JsonProperty("numberOfDependents")
    public int getNumberOfDependents() {
        return numberOfDependents;
    }

    @JsonProperty("numberOfDependents")
    public void setNumberOfDependents(int numberOfDependents) {
        this.numberOfDependents = numberOfDependents;
    }

    @JsonProperty("numberOfContributionsAndPayments")
    public int getNumberOfContributionsAndPayments() {
        return numberOfContributionsAndPayments;
    }

    @JsonProperty("numberOfContributionsAndPayments")
    public void setNumberOfContributionsAndPayments(int numberOfContributionsAndPayments) {
        this.numberOfContributionsAndPayments = numberOfContributionsAndPayments;
    }

    @JsonProperty("numberOfEmployeeContributions")
    public int getNumberOfEmployeeContributions() {
        return numberOfEmployeeContributions;
    }

    @JsonProperty("numberOfEmployeeContributions")
    public void setNumberOfEmployeeContributions(int numberOfEmployeeContributions) {
        this.numberOfEmployeeContributions = numberOfEmployeeContributions;
    }

    @JsonProperty("numberOfEmployerContributions")
    public int getNumberOfEmployerContributions() {
        return numberOfEmployerContributions;
    }

    @JsonProperty("numberOfEmployerContributions")
    public void setNumberOfEmployerContributions(int numberOfEmployerContributions) {
        this.numberOfEmployerContributions = numberOfEmployerContributions;
    }

    @JsonProperty("numberOfBalances")
    public int getNumberOfBalances() {
        return numberOfBalances;
    }

    @JsonProperty("numberOfBalances")
    public void setNumberOfBalances(int numberOfBalances) {
        this.numberOfBalances = numberOfBalances;
    }

    @JsonProperty("numberOfClaims")
    public int getNumberOfClaims() {
        return numberOfClaims;
    }

    @JsonProperty("numberOfClaims")
    public void setNumberOfClaims(int numberOfClaims) {
        this.numberOfClaims = numberOfClaims;
    }

    @JsonProperty("totalContributionsAndPayments")
    public Float getTotalContributionsAndPayments() {
        return totalContributionsAndPayments;
    }

    @JsonProperty("totalContributionsAndPayments")
    public void setTotalContributionsAndPayments(Float totalContributionsAndPayments) {
        this.totalContributionsAndPayments = totalContributionsAndPayments;
    }

    @JsonProperty("totalOfBalances")
    public Float getTotalOfBalances() {
        return totalOfBalances;
    }

    @JsonProperty("totalOfBalances")
    public void setTotalOfBalances(Float totalOfBalances) {
        this.totalOfBalances = totalOfBalances;
    }

    @JsonProperty("totalClaimsRepricedAmount")
    public Float getTotalClaimsRepricedAmount() {
        return totalClaimsRepricedAmount;
    }

    @JsonProperty("totalClaimsRepricedAmount")
    public void setTotalClaimsRepricedAmount(Float totalClaimsRepricedAmount) {
        this.totalClaimsRepricedAmount = totalClaimsRepricedAmount;
    }

    @JsonProperty("totalClaimsPatientResponsibilityAmount")
    public Float getTotalClaimsPatientResponsibilityAmount() {
        return totalClaimsPatientResponsibilityAmount;
    }

    @JsonProperty("totalClaimsPatientResponsibilityAmount")
    public void setTotalClaimsPatientResponsibilityAmount(Float totalClaimsPatientResponsibilityAmount) {
        this.totalClaimsPatientResponsibilityAmount = totalClaimsPatientResponsibilityAmount;
    }

    @JsonIgnore
    private void setContributionInfo(ArrayList<ContributionsAndPayments> contributionsAndPayments)
    {
        if (contributionsAndPayments != null)
        {
            this.numberOfContributionsAndPayments = contributionsAndPayments.size();

            int numEmployeeContributions = 0;
            int numEmployerContributions = 0;
            int numPayments = 0;
            Float totalContributionsAndPayments = 0.0F;
            Float totalEmployeeContributions = 0.0F;
            Float totalEmployerContributions = 0.0F;
            Float totalPayments = 0.0F;

            for (ContributionsAndPayments cp : contributionsAndPayments)
            {
                if (cp.getCategory().equalsIgnoreCase("DistNormal"))
                {
                    numPayments++;
                    if (cp.getAmount() != null) {
                        totalPayments = totalPayments += cp.getAmount();
                    }
                }
                else if (cp.getCategory().equalsIgnoreCase("ContEmployee"))
                {
                    numEmployeeContributions++;

                    if (cp.getAmount() != null) {
                        totalEmployeeContributions = totalEmployeeContributions += cp.getAmount();
                    }
                }
                else if (cp.getCategory().equalsIgnoreCase("ContEmployer"))
                {
                    numEmployerContributions++;

                    if (cp.getAmount() != null) {
                        totalEmployerContributions = totalEmployerContributions += cp.getAmount();
                    }
                }

                if (cp.getAmount() != null) {
                    totalContributionsAndPayments = totalContributionsAndPayments += cp.getAmount();
                }
            }

            setNumberOfEmployeeContributions(numEmployeeContributions);
            setNumberOfEmployerContributions(numEmployerContributions);
            setNumberofPayments(numPayments);
            setTotalContributionsAndPayments(totalContributionsAndPayments);

            setTotalPayments(totalPayments);
            setTotalEmployeeContributions(totalEmployeeContributions);
            setTotalEmployerContributions(totalEmployerContributions);
        }

    }

    @JsonProperty("totalEmployeeContributions")
    public Float getTotalEmployeeContributions() {
        return totalEmployeeContributions;
    }

    @JsonProperty("totalEmployeeContributions")
    public void setTotalEmployeeContributions(Float totalEmployeeContributions) {
        this.totalEmployeeContributions = totalEmployeeContributions;
    }

    @JsonProperty("totalEmployerContributions")
    public Float getTotalEmployerContributions() {
        return totalEmployerContributions;
    }

    @JsonProperty("totalEmployerContributions")
    public void setTotalEmployerContributions(Float totalEmployerContributions) {
        this.totalEmployerContributions = totalEmployerContributions;
    }

    @JsonProperty("totalPayments")
    public Float getTotalPayments() {
        return totalPayments;
    }

    @JsonProperty("totalPayments")
    public void setTotalPayments(Float totalPayments) {
        this.totalPayments = totalPayments;
    }

    @JsonProperty("numberofPayments")
    public int getNumberofPayments() {
        return numberofPayments;
    }

    @JsonProperty("numberofPayments")
    public void setNumberofPayments(int numberofPayments) {
        this.numberofPayments = numberofPayments;
    }

    @JsonProperty("cptCodesAll")
    public List<String> getCptCodesAll() {
        return cptCodesAll;
    }

    @JsonProperty("cptCodesAll")
    public void setCptCodesAll(List<String> cptCodesAll) {
        this.cptCodesAll = cptCodesAll;
    }

    @JsonIgnore
    public void addCptCodesAll(List<String> cptCodesAll) {
        if (cptCodesAll == null) 
            return;
        
        if (this.cptCodesAll == null)
        {
            this.cptCodesAll = new ArrayList<>();
        }
        
        this.cptCodesAll.addAll(cptCodesAll);
    }

    @JsonProperty("cptCodesUnique")
    public Set<String> getCptCodesUnique() {
        return cptCodesUnique;
    }

    @JsonProperty("cptCodesUnique")
    public void setCptCodesUnique(Set<String> cptCodesUnique) {
        this.cptCodesUnique = cptCodesUnique;
    }

    @JsonIgnore
    public void addCptCodesUnique(Set<String> cptCodesUnique) {
        if (cptCodesUnique == null) 
            return;

        if (this.cptCodesUnique == null)
        {
            this.cptCodesUnique = new HashSet<>();
        }

        this.cptCodesUnique.addAll(cptCodesUnique);
    }
}
