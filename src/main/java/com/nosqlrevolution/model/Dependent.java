package com.nosqlrevolution.model;

public class Dependent {
    private int newMemberID;
    private int dependentID;
    private String relationship;
    private String birthYear;
    private String gender;
    private String state;
    private String zip;

    public int getNewMemberID() {
        return newMemberID;
    }

    public void setNewMemberID(int newMemberID) {
        this.newMemberID = newMemberID;
    }

    public int getDependentID() {
        return dependentID;
    }

    public void setDependentID(int dependentID) {
        this.dependentID = dependentID;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
