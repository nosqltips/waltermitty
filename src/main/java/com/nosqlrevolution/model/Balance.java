package com.nosqlrevolution.model;

import java.math.BigDecimal;

public class Balance {
    private int newMemberID;
    private BigDecimal cachedBalance;

    public int getNewMemberID() {
        return newMemberID;
    }

    public void setNewMemberID(int newMemberID) {
        this.newMemberID = newMemberID;
    }

    public BigDecimal getCachedBalance() {
        return cachedBalance;
    }

    public void setCachedBalance(BigDecimal cachedBalance) {
        this.cachedBalance = cachedBalance;
    }
}
