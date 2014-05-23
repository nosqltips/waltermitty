package com.nosqlrevolution.waltermittyclient.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ContributionsAndPayments {
    private int newMemberID;
    private BigDecimal amount;
    private String category;
    private Timestamp paymentAvailableDate;

    @JsonProperty("newMemberID")
    public int getNewMemberID() {
        return newMemberID;
    }

    @JsonProperty("newMemberID")
    public void setNewMemberID(int newMemberID) {
        this.newMemberID = newMemberID;
    }

    @JsonProperty("amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("paymentAvailableDate")
    public Timestamp getPaymentAvailableDate() {
        return paymentAvailableDate;
    }

    @JsonProperty("paymentAvailableDate")
    public void setPaymentAvailableDate(Timestamp paymentAvailableDate) {
        this.paymentAvailableDate = paymentAvailableDate;
    }
}
