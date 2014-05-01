package com.nosqlrevolution.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClaimDetail {
    private int newClaimID;
    private String cptCode;

    @JsonProperty("newClaimID")
    public int getNewClaimID() {
        return newClaimID;
    }

    @JsonProperty("newClaimID")
    public void setNewClaimID(int newClaimID) {
        this.newClaimID = newClaimID;
    }

    @JsonProperty("cptCode")
    public String getCptCode() {
        return cptCode;
    }

    @JsonProperty("cptCode")
    public void setCptCode(String cptCode) {
        this.cptCode = cptCode;
    }
}
