package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class Result implements Serializable {
    private String memberId;
    private String state;
    private String zip;
    private String birthYear;
    private String numDependents;
    private String numPayments;
    private String numClaims;
    
    private Float score;
            
    public Result() {}

    @JsonProperty("memberId")
    public String getMemberId() {
        return memberId;
    }

    @JsonProperty("memberId")
    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    @JsonProperty("birthYear")
    public String getBirthYear() {
        return birthYear;
    }

    @JsonProperty("birthYear")
    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    @JsonProperty("numDependents")
    public String getNumDependents() {
        return numDependents;
    }

    @JsonProperty("numDependents")
    public void setNumDependents(String numDependents) {
        this.numDependents = numDependents;
    }

    @JsonProperty("numPayments")
    public String getNumPayments() {
        return numPayments;
    }

    @JsonProperty("numPayments")
    public void setNumPayments(String numPayments) {
        this.numPayments = numPayments;
    }

    @JsonProperty("numClaims")
    public String getNumClaims() {
        return numClaims;
    }

    @JsonProperty("numClaims")
    public void setNumClaims(String numClaims) {
        this.numClaims = numClaims;
    }
    
    @JsonProperty("score")
    public float getScore() {
        return score;
    }

    @JsonProperty("score")
    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getMemberId()!= null ? getMemberId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Result)) {
            return false;
        }
        Result other = (Result) object;
        return (this.getMemberId() != null || other.getMemberId() == null) && (this.getMemberId() == null || this.getMemberId().equals(other.getMemberId()));
    }

    @Override
    public String toString() {
        return "Result[ memberId=" + getMemberId() + " ]";
    }
}
