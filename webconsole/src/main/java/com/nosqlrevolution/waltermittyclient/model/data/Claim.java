package com.nosqlrevolution.waltermittyclient.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private Float repricedAmount;
    private Float patientResponsibilityAmount;

    private ArrayList<ClaimDetail> claimDetails;
    private List<String> cptCodesAll = new ArrayList<>();
    private Set<String> cptCodesUnique = new HashSet<>();

    @JsonProperty("newClaimID")
    public int getNewClaimID() {
        return newClaimID;
    }

    @JsonProperty("newClaimID")
    public void setNewClaimID(int newClaimID) {
        this.newClaimID = newClaimID;
    }

    @JsonProperty("newMemberID")
    public int getNewMemberID() {
        return newMemberID;
    }

    @JsonProperty("newMemberID")
    public void setNewMemberID(int newMemberID) {
        this.newMemberID = newMemberID;
    }

    @JsonProperty("dependentServiced")
    public int getDependentServiced() {
        return dependentServiced;
    }

    @JsonProperty("dependentServiced")
    public void setDependentServiced(int dependentServiced) {
        this.dependentServiced = dependentServiced;
    }

    @JsonProperty("claimType")
    public String getClaimType() {
        return claimType;
    }

    @JsonProperty("claimType")
    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    @JsonProperty("dateReceived")
    public Timestamp getDateReceived() {
        return dateReceived;
    }

    @JsonProperty("dateReceived")
    public void setDateReceived(Timestamp dateReceived) {
        this.dateReceived = dateReceived;
    }

    @JsonProperty("dateProcessed")
    public Timestamp getDateProcessed() {
        return dateProcessed;
    }

    @JsonProperty("dateProcessed")
    public void setDateProcessed(Timestamp dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    @JsonProperty("serviceStart")
    public Timestamp getServiceStart() {
        return serviceStart;
    }

    @JsonProperty("serviceStart")
    public void setServiceStart(Timestamp serviceStart) {
        this.serviceStart = serviceStart;
    }

    @JsonProperty("serviceEnd")
    public Timestamp getServiceEnd() {
        return serviceEnd;
    }

    @JsonProperty("serviceEnd")
    public void setServiceEnd(Timestamp serviceEnd) {
        this.serviceEnd = serviceEnd;
    }

    @JsonProperty("repricedAmount")
    public Float getRepricedAmount() {
        return repricedAmount;
    }

    @JsonProperty("repricedAmount")
    public void setRepricedAmount(Float repricedAmount) {
        this.repricedAmount = repricedAmount;
    }

    @JsonProperty("patientResponsibilityAmount")
    public Float getPatientResponsibilityAmount() {
        return patientResponsibilityAmount;
    }

    @JsonProperty("patientResponsibilityAmount")
    public void setPatientResponsibilityAmount(Float patientResponsibilityAmount) {
        this.patientResponsibilityAmount = patientResponsibilityAmount;
    }

    @JsonProperty("claimDetails")
    public ArrayList<ClaimDetail> getClaimDetails() {
        return claimDetails;
    }

    @JsonProperty("claimDetails")
    public void setClaimDetails(ArrayList<ClaimDetail> claimDetails) {
        this.claimDetails = claimDetails;
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
            this.cptCodesAll = new ArrayList<String>();
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
            this.cptCodesUnique = new HashSet<String>();
        }

        this.cptCodesUnique.addAll(cptCodesUnique);
    }
}
