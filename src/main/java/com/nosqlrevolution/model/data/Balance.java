package com.nosqlrevolution.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Balance {
    private int newMemberID;
    private Float cachedBalance;

    @JsonProperty("newMemberID")
    public int getNewMemberID() {
        return newMemberID;
    }

    @JsonProperty("newMemberID")
    public void setNewMemberID(int newMemberID) {
        this.newMemberID = newMemberID;
    }

    @JsonProperty("cachedBalance")
    public Float getCachedBalance() {
        return cachedBalance;
    }

    @JsonProperty("cachedBalance")
    public void setCachedBalance(Float cachedBalance) {
        this.cachedBalance = cachedBalance;
    }
}
