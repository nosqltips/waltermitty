package com.nosqlrevolution.enums;

import static com.nosqlrevolution.enums.ChartType.*;

/**
 *
 * @author cbrown
 */
public enum ChartField {
    STATE("state", GEO),
    GENDER("gender", BAR),
    BIRTH_YEAR("birthYear", BAR),
    
    MEMBER_CONTRIBUTIONS("memberContributions.paymentAvailableDate", LINE),
    MEMBER_CONTRIBUTIONS_AMOUNT("memberContributions.amount", LINE),
    COMPANY_CONTRIBUTIONS("companyContributions.paymentAvailableDate", LINE),
    COMPANY_CONTRIBUTIONS_AMOUNT("companyContributions.amount", LINE),
    MEMBER_PAYMENTS("memberPayments.paymentAvailableDate", LINE),
    MEMBER_PAYMENTS_AMOUNT("memberPayments.amount", LINE)
    ;
    
    private final String name;
    private final ChartType type;
    ChartField(String name, ChartType type) {
        this.name = name;
        this.type = type;
    }
    public String getName() { return name; }
    public ChartType getType() { return type; }
}
