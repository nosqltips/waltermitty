package com.nosqlrevolution.enums;

/**
 *
 * @author cbrown
 */
public enum SearchField {
    MEMBER_ID("memberId"),
    STATE("state"),
    ZIP("zip"),
    GENDER("gender"),
    BIRTH_YEAR("birthYear"),
    NUM_DEPENDENTS("numberOfDependents"),
    NUM_CONTRIBUTIONS("numberOfContributionsAndPayments"),
    NUM_EMPLOYEE_CONTRIBUTIONS("numberOfEmployeeContributions"),
    NUM_EMPOYER_CONTRIBUTIONS("numberOfEmployerContributions"),
    NUM_PAYMENTS("numberofPayments"),
    TOTAL_EMPLOYEE_CONTRIBUTIONS("totalEmployeeContributions"),
    TOTAL_EMPLOYER_CONTRIBUTIONS("totalEmployerContributions"),
    TOTAL_PAYMENTS("totalPayments"),
    TOTAL_CONTRIBUTIONS("totalContributionsAndPayments"),
    NUM_BALANCES("numberOfBalances"),
    TOTAL_BALANCES("totalOfBalances"),
    NUM_CLAIMS("numberOfClaims"),
    TOTAL_CLAIMS("totalClaimsRepricedAmount"),
    TOTAL_CLAIMS_PATIENT("totalClaimsPatientResponsibilityAmount");
    
    private final String name;
    SearchField(String name) {
        this.name = name;
    }
    public String getName() { return name; }
    public static String[] getAllFields() {
        String[] all = new String[values().length];
        for (int i=0; i< values().length; i++) {
            all[i] = values()[i].getName();
        }
        
        return all;
    }
}
