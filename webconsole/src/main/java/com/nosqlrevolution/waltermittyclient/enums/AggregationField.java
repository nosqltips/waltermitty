package com.nosqlrevolution.waltermittyclient.enums;

/**
 *
 * @author cbrown
 */
public enum AggregationField {
    STATE("state", "State"),
    ZIP("zip", "Zip"),
    GENDER("gender", "Gender"),
    BIRTH_YEAR("birthYear", "Birth Year"),
    NUM_DEPENDENTS("numberOfDependents", "Number of Dependents"),
    NUM_CONTRIBUTIONS("numberOfContributionsAndPayments", "Number of Contributions"),
    NUM_EMPLOYEE_CONTRIBUTIONS("numberOfEmployeeContributions", "Number of Employee Contributions"),
    NUM_EMPOYER_CONTRIBUTIONS("numberOfEmployerContributions", "Number of Employer Contributions"),
    NUM_PAYMENTS("numberofPayments", "Number of Payments"),
    TOTAL_EMPLOYEE_CONTRIBUTIONS("totalEmployeeContributions", "Total Employee Contributions"),
    TOTAL_EMPLOYER_CONTRIBUTIONS("totalEmployerContributions", "Total Employer Contributions"),
    TOTAL_PAYMENTS("totalPayments", "Total Payments"),
    TOTAL_CONTRIBUTIONS("totalContributionsAndPayments", "Total Contributions and Payments"),
    NUM_BALANCES("numberOfBalances", "Number of Balances"),
    TOTAL_BALANCES("totalOfBalances", "Total Balances"),
    NUM_CLAIMS("numberOfClaims", "Number of Claims"),
    TOTAL_CLAIMS("totalClaimsRepricedAmount", "Total Claims"),
    TOTAL_CLAIMS_PATIENT("totalClaimsPatientResponsibilityAmount", "Total Claims Patient"),
    CPT_CODES_ALL("cptCodesAll", "CPT Codes All"),
    CPT_CODES_UNIQUE("cptCodesUnique", "CPT Codes Unique"),
    
    CLAIM_TYPE("contributionsAndPayments.category", "Claim Type");
    
    private final String name;
    private final String display;
    AggregationField(String name, String display) {
        this.name = name;
        this.display = display;
    }
    public String getName() { return name; }
    public String getDisplay() { return display; }
}
