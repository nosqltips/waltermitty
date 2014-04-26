package com.nosqlrevolution.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ContributionsAndPayments {
    private int newMemberID;
    private BigDecimal amount;
    private String category;
    private Timestamp paymentAvailableDate;

    public int getNewMemberID() {
        return newMemberID;
    }

    public void setNewMemberID(int newMemberID) {
        this.newMemberID = newMemberID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Timestamp getPaymentAvailableDate() {
        return paymentAvailableDate;
    }

    public void setPaymentAvailableDate(Timestamp paymentAvailableDate) {
        this.paymentAvailableDate = paymentAvailableDate;
    }
}
