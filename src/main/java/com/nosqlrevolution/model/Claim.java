package com.nosqlrevolution.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Claim {
    private int newClaimID;
    private int newMemberID;
    private int dependentServiced;
    private String claimType;
    private Timestamp dateReceived;
    private Timestamp dateProcessed;
    private Timestamp serviceStart;
    private Timestamp serviceEnd;
    private BigDecimal repricedAmount;
    private BigDecimal patientResponsibilityAmount;

    private ArrayList<ClaimDetail> claimDetails;
    private List<String> cptCodesAll = new ArrayList<String>();
    private Set<String> cptCodesUnique = new HashSet<String>();

    
    public int getNewClaimID() {
        return newClaimID;
    }

    public void setNewClaimID(int newClaimID) {
        this.newClaimID = newClaimID;
    }

    public int getNewMemberID() {
        return newMemberID;
    }

    public void setNewMemberID(int newMemberID) {
        this.newMemberID = newMemberID;
    }

    public int getDependentServiced() {
        return dependentServiced;
    }

    public void setDependentServiced(int dependentServiced) {
        this.dependentServiced = dependentServiced;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public Timestamp getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Timestamp dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Timestamp getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(Timestamp dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public Timestamp getServiceStart() {
        return serviceStart;
    }

    public void setServiceStart(Timestamp serviceStart) {
        this.serviceStart = serviceStart;
    }

    public Timestamp getServiceEnd() {
        return serviceEnd;
    }

    public void setServiceEnd(Timestamp serviceEnd) {
        this.serviceEnd = serviceEnd;
    }

    public BigDecimal getRepricedAmount() {
        return repricedAmount;
    }

    public void setRepricedAmount(BigDecimal repricedAmount) {
        this.repricedAmount = repricedAmount;
    }

    public BigDecimal getPatientResponsibilityAmount() {
        return patientResponsibilityAmount;
    }

    public void setPatientResponsibilityAmount(BigDecimal patientResponsibilityAmount) {
        this.patientResponsibilityAmount = patientResponsibilityAmount;
    }

    public ArrayList<ClaimDetail> getClaimDetails() {
        return claimDetails;
    }

    public void setClaimDetails(ArrayList<ClaimDetail> claimDetails) {
        this.claimDetails = claimDetails;
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
