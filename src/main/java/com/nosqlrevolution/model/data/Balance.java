package com.nosqlrevolution.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class Balance {
    private int newMemberID;
    private BigDecimal cachedBalance;

    @JsonProperty("newMemberID")
    public int getNewMemberID() {
        return newMemberID;
    }

    @JsonProperty("newMemberID")
    public void setNewMemberID(int newMemberID) {
        this.newMemberID = newMemberID;
    }

    @JsonProperty("cachedBalance")
    public BigDecimal getCachedBalance() {
        return cachedBalance;
    }

    @JsonProperty("cachedBalance")
    public void setCachedBalance(BigDecimal cachedBalance) {
        this.cachedBalance = cachedBalance;
    }
}
