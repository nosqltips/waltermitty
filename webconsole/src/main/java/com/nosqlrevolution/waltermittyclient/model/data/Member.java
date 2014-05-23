package com.nosqlrevolution.waltermittyclient.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
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
    private Timestamp hsaEffectiveDate;

    private int numberOfDependents;
    private ArrayList<Dependent> dependents;

    private int numberOfContributionsAndPayments;
    private int numberOfEmployeeContributions;
    private int numberOfEmployerContributions;
    private int numberofPayments;
    private BigDecimal totalEmployeeContributions;  // total dollar amount of employee contributions
    private BigDecimal totalEmployerContributions;  // total dollar amount of EMPLOYER contributions
    private BigDecimal totalPayments;  // total dollar amount of payments
    private BigDecimal totalContributionsAndPayments;
    private ArrayList<ContributionsAndPayments> contributionsAndPayments;

    private int numberOfBalances;
    private BigDecimal totalOfBalances;
    private ArrayList<Balance> balances;

    private int numberOfClaims;
    private BigDecimal totalClaimsRepricedAmount;
    private BigDecimal totalClaimsPatientResponsibilityAmount;
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

            BigDecimal totalOfBalances = new BigDecimal(0);
            for (Balance balance : balances)
            {
                if (balance.getCachedBalance() != null)
                {
                    totalOfBalances = totalOfBalances.add(balance.getCachedBalance());
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

            BigDecimal totalClaimsRepricedAmount = new BigDecimal(0);
            BigDecimal totalClaimsPatientResponsibilityAmount = new BigDecimal(0);
            for (Claim claim : claims)
            {
                if (claim.getRepricedAmount() != null)
                {
                    totalClaimsRepricedAmount = totalClaimsRepricedAmount.add(claim.getRepricedAmount());
                }

                if (claim.getPatientResponsibilityAmount() != null)
                {
                    totalClaimsPatientResponsibilityAmount = totalClaimsPatientResponsibilityAmount.add(claim.getPatientResponsibilityAmount());
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
    public BigDecimal getTotalContributionsAndPayments() {
        return totalContributionsAndPayments;
    }

    @JsonProperty("totalContributionsAndPayments")
    public void setTotalContributionsAndPayments(BigDecimal totalContributionsAndPayments) {
        this.totalContributionsAndPayments = totalContributionsAndPayments;
    }

    @JsonProperty("totalOfBalances")
    public BigDecimal getTotalOfBalances() {
        return totalOfBalances;
    }

    @JsonProperty("totalOfBalances")
    public void setTotalOfBalances(BigDecimal totalOfBalances) {
        this.totalOfBalances = totalOfBalances;
    }

    @JsonProperty("totalClaimsRepricedAmount")
    public BigDecimal getTotalClaimsRepricedAmount() {
        return totalClaimsRepricedAmount;
    }

    @JsonProperty("totalClaimsRepricedAmount")
    public void setTotalClaimsRepricedAmount(BigDecimal totalClaimsRepricedAmount) {
        this.totalClaimsRepricedAmount = totalClaimsRepricedAmount;
    }

    @JsonProperty("totalClaimsPatientResponsibilityAmount")
    public BigDecimal getTotalClaimsPatientResponsibilityAmount() {
        return totalClaimsPatientResponsibilityAmount;
    }

    @JsonProperty("totalClaimsPatientResponsibilityAmount")
    public void setTotalClaimsPatientResponsibilityAmount(BigDecimal totalClaimsPatientResponsibilityAmount) {
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
            BigDecimal totalContributionsAndPayments = new BigDecimal(0);
            BigDecimal totalEmployeeContributions = new BigDecimal(0);
            BigDecimal totalEmployerContributions = new BigDecimal(0);
            BigDecimal totalPayments = new BigDecimal(0);

            for (ContributionsAndPayments cp : contributionsAndPayments)
            {
                if (cp.getCategory().equalsIgnoreCase("DistNormal"))
                {
                    numPayments++;
                    if (cp.getAmount() != null) {
                        totalPayments = totalPayments.add(cp.getAmount());
                    }
                }
                else if (cp.getCategory().equalsIgnoreCase("ContEmployee"))
                {
                    numEmployeeContributions++;

                    if (cp.getAmount() != null) {
                        totalEmployeeContributions = totalEmployeeContributions.add(cp.getAmount());
                    }
                }
                else if (cp.getCategory().equalsIgnoreCase("ContEmployer"))
                {
                    numEmployerContributions++;

                    if (cp.getAmount() != null) {
                        totalEmployerContributions = totalEmployerContributions.add(cp.getAmount());
                    }
                }

                if (cp.getAmount() != null) {
                    totalContributionsAndPayments = totalContributionsAndPayments.add(cp.getAmount());
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
    public BigDecimal getTotalEmployeeContributions() {
        return totalEmployeeContributions;
    }

    @JsonProperty("totalEmployeeContributions")
    public void setTotalEmployeeContributions(BigDecimal totalEmployeeContributions) {
        this.totalEmployeeContributions = totalEmployeeContributions;
    }

    @JsonProperty("totalEmployerContributions")
    public BigDecimal getTotalEmployerContributions() {
        return totalEmployerContributions;
    }

    @JsonProperty("totalEmployerContributions")
    public void setTotalEmployerContributions(BigDecimal totalEmployerContributions) {
        this.totalEmployerContributions = totalEmployerContributions;
    }

    @JsonProperty("totalPayments")
    public BigDecimal getTotalPayments() {
        return totalPayments;
    }

    @JsonProperty("totalPayments")
    public void setTotalPayments(BigDecimal totalPayments) {
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
