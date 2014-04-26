package com.nosqlrevolution.model;

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

    private List<String> cptCodesAll = new ArrayList<String>();
    private Set<String> cptCodesUnique = new HashSet<String>();

    public int getNewMemberID() {
        return newMemberID;
    }

    public void setNewMemberID(int newMemberID) {
        this.newMemberID = newMemberID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public Timestamp getHsaEffectiveDate() {
        return hsaEffectiveDate;
    }

    public void setHsaEffectiveDate(Timestamp hsaEffectiveDate) {
        this.hsaEffectiveDate = hsaEffectiveDate;
    }

    public ArrayList<Dependent> getDependents() {
        return dependents;
    }

    public void setDependents(ArrayList<Dependent> dependents) {
        this.dependents = dependents;

        if (dependents != null)
        {
            setNumberOfDependents(dependents.size());
        }
    }

    public ArrayList<ContributionsAndPayments> getContributionsAndPayments() {
        return contributionsAndPayments;
    }

    public void setContributionsAndPayments(ArrayList<ContributionsAndPayments> contributionsAndPayments) {
        this.contributionsAndPayments = contributionsAndPayments;

        if (contributionsAndPayments != null)
        {
            setContributionInfo(contributionsAndPayments);
        }

    }

    public ArrayList<Balance> getBalances() {
        return balances;
    }

    public void setBalances(ArrayList<Balance> balances)
    {
        this.balances = balances;

        setBalancesInfo(balances);
    }

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

    public ArrayList<Claim> getClaims() {
        return claims;
    }

    public void setClaims(ArrayList<Claim> claims)
    {
        this.claims = claims;

        setClaimsInfo(claims);
    }

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


    public int getNumberOfDependents() {
        return numberOfDependents;
    }

    public void setNumberOfDependents(int numberOfDependents) {
        this.numberOfDependents = numberOfDependents;
    }

    public int getNumberOfContributionsAndPayments() {
        return numberOfContributionsAndPayments;
    }

    public void setNumberOfContributionsAndPayments(int numberOfContributionsAndPayments) {
        this.numberOfContributionsAndPayments = numberOfContributionsAndPayments;
    }

    public int getNumberOfEmployeeContributions() {
        return numberOfEmployeeContributions;
    }

    public void setNumberOfEmployeeContributions(int numberOfEmployeeContributions) {
        this.numberOfEmployeeContributions = numberOfEmployeeContributions;
    }

    public int getNumberOfEmployerContributions() {
        return numberOfEmployerContributions;
    }

    public void setNumberOfEmployerContributions(int numberOfEmployerContributions) {
        this.numberOfEmployerContributions = numberOfEmployerContributions;
    }

    public int getNumberOfBalances() {
        return numberOfBalances;
    }

    public void setNumberOfBalances(int numberOfBalances) {
        this.numberOfBalances = numberOfBalances;
    }

    public int getNumberOfClaims() {
        return numberOfClaims;
    }

    public void setNumberOfClaims(int numberOfClaims) {
        this.numberOfClaims = numberOfClaims;
    }

    public BigDecimal getTotalContributionsAndPayments() {
        return totalContributionsAndPayments;
    }

    public void setTotalContributionsAndPayments(BigDecimal totalContributionsAndPayments) {
        this.totalContributionsAndPayments = totalContributionsAndPayments;
    }

    public BigDecimal getTotalOfBalances() {
        return totalOfBalances;
    }

    public void setTotalOfBalances(BigDecimal totalOfBalances) {
        this.totalOfBalances = totalOfBalances;
    }

    public BigDecimal getTotalClaimsRepricedAmount() {
        return totalClaimsRepricedAmount;
    }

    public void setTotalClaimsRepricedAmount(BigDecimal totalClaimsRepricedAmount) {
        this.totalClaimsRepricedAmount = totalClaimsRepricedAmount;
    }

    public BigDecimal getTotalClaimsPatientResponsibilityAmount() {
        return totalClaimsPatientResponsibilityAmount;
    }

    public void setTotalClaimsPatientResponsibilityAmount(BigDecimal totalClaimsPatientResponsibilityAmount) {
        this.totalClaimsPatientResponsibilityAmount = totalClaimsPatientResponsibilityAmount;
    }

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

    public BigDecimal getTotalEmployeeContributions() {
        return totalEmployeeContributions;
    }

    public void setTotalEmployeeContributions(BigDecimal totalEmployeeContributions) {
        this.totalEmployeeContributions = totalEmployeeContributions;
    }

    public BigDecimal getTotalEmployerContributions() {
        return totalEmployerContributions;
    }

    public void setTotalEmployerContributions(BigDecimal totalEmployerContributions) {
        this.totalEmployerContributions = totalEmployerContributions;
    }

    public BigDecimal getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(BigDecimal totalPayments) {
        this.totalPayments = totalPayments;
    }

    public int getNumberofPayments() {
        return numberofPayments;
    }

    public void setNumberofPayments(int numberofPayments) {
        this.numberofPayments = numberofPayments;
    }

    public List<String> getCptCodesAll() {
        return cptCodesAll;
    }

    public void setCptCodesAll(List<String> cptCodesAll) {
        this.cptCodesAll = cptCodesAll;
    }

    public void addCptCodesAll(List<String> cptCodesAll) {
        if (cptCodesAll == null) 
            return;
        
        if (this.cptCodesAll == null)
        {
            this.cptCodesAll = new ArrayList<String>();
        }
        
        this.cptCodesAll.addAll(cptCodesAll);
    }

    public Set<String> getCptCodesUnique() {
        return cptCodesUnique;
    }

    public void setCptCodesUnique(Set<String> cptCodesUnique) {
        this.cptCodesUnique = cptCodesUnique;
    }

    public void addCptCodesUnique(Set<String> cptCodesUnique) {
        if (cptCodesUnique == null) 
            return;

        if (this.cptCodesUnique == null)
        {
            this.cptCodesUnique = new HashSet<String>();
        }

        this.cptCodesUnique.addAll(cptCodesUnique);
    }
}
