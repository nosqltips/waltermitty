package com.nosqlrevolution.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dependent {
    private int newMemberID;
    private int dependentID;
    private String relationship;
    private String birthYear;
    private Integer birthYearNum;
    private String birthDecade;
    private Integer age;
    private String gender;
    private String state;
    private String zip;

    @JsonProperty("newMemberID")
    public int getNewMemberID() {
        return newMemberID;
    }

    @JsonProperty("newMemberID")
    public void setNewMemberID(int newMemberID) {
        this.newMemberID = newMemberID;
    }

    @JsonProperty("dependentID")
    public int getDependentID() {
        return dependentID;
    }

    @JsonProperty("dependentID")
    public void setDependentID(int dependentID) {
        this.dependentID = dependentID;
    }

    @JsonProperty("relationship")
    public String getRelationship() {
        return relationship;
    }

    @JsonProperty("relationship")
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @JsonProperty("birthYear")
    public String getBirthYear() {
        return birthYear;
    }

    @JsonProperty("birthYear")
    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    @JsonProperty("birthYearNum")
    public Integer getBirthYearNum() {
        return birthYearNum;
    }

    @JsonProperty("birthYearNum")
    public void setBirthYearNum(Integer birthYearNum) {
        this.birthYearNum = birthYearNum;
    }

    @JsonProperty("birthDecade")
    public String getBirthDecade() {
        return birthDecade;
    }

    @JsonProperty("birthDecade")
    public void setBirthDecade(String birthDecade) {
        this.birthDecade = birthDecade;
    }

    @JsonProperty("age")
    public Integer getAge() {
        return age;
    }

    @JsonProperty("age")
    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
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
}
