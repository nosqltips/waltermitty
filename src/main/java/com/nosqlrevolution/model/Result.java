package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class Result implements Serializable {
    private String memberId;
    private String state;
    private String zip;
    private String birthYear;
    private String gender;
    private Integer numDependents;
    private Integer numPayments;
    private Integer numClaims;
    
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

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("numDependents")
    public Integer getNumDependents() {
        return numDependents;
    }

    @JsonProperty("numDependents")
    public void setNumDependents(Integer numDependents) {
        this.numDependents = numDependents;
    }

    @JsonProperty("numPayments")
    public Integer getNumPayments() {
        return numPayments;
    }

    @JsonProperty("numPayments")
    public void setNumPayments(Integer numPayments) {
        this.numPayments = numPayments;
    }

    @JsonProperty("numClaims")
    public Integer getNumClaims() {
        return numClaims;
    }

    @JsonProperty("numClaims")
    public void setNumClaims(Integer numClaims) {
        this.numClaims = numClaims;
    }
    
    @JsonProperty("score")
    public Float getScore() {
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
