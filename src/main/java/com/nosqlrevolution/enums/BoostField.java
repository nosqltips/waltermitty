package com.nosqlrevolution.enums;

/**
 *
 * @author cbrown
 */
public enum BoostField {
    STATE("State", 1.7F),
    GENDER("Gender", 1.5F),
    BIRTH_YEAR("Birth Year", 1.5F),
    NUM_DEPENDENTS("Number of Dependents", 1.2F),
    NUM_CONTRIBUTIONS("Number of Contributions", 1.0F),
    NUM_PAYMENTS("Number of Payments", 1.0F),
    NUM_CLAIMS("Number of Claims", 1.0F),
    TOTAL_PAYMENTS("Total Payments", 1.0F),
    TOTAL_CONTRIBUTIONS("Total Contributions and Payments", 1.0F),
    TOTAL_BALANCES("Total Balances", 1.0F),
    TOTAL_CLAIMS("Total Claims", 1.0F),
    CPT_CODES_UNIQUE("CPT Codes Unique", 1.5F);   
    
    private final String display;
    private final Float defaultBoost;
    BoostField(String display, Float defaultBoost) {
        this.display = display;
        this.defaultBoost = defaultBoost;
    }
    public String getDisplay() { return display; }
    public Float getDefaultBoost() { return defaultBoost; }
}
