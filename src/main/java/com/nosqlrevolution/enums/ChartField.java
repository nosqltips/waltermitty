package com.nosqlrevolution.enums;

import static com.nosqlrevolution.enums.ChartType.*;

/**
 *
 * @author cbrown
 */
public enum ChartField {
    STATE("state", null, GEO),
    GENDER("gender", null, BAR),
    BIRTH_YEAR("birthYearNum", null, BAR),
    
    MEMBER_CONTRIBUTIONS("memberContributions.paymentAvailableDate", "memberContributions", LINE),
    MEMBER_CONTRIBUTIONS_AMOUNT("memberContributions.amount", "memberContributions", LINE),
    COMPANY_CONTRIBUTIONS("companyContributions.paymentAvailableDate", "companyContributions", LINE),
    COMPANY_CONTRIBUTIONS_AMOUNT("companyContributions.amount", "companyContributions", LINE),
    MEMBER_PAYMENTS("memberPayments.paymentAvailableDate", "memberPayments", LINE),
    MEMBER_PAYMENTS_AMOUNT("memberPayments.amount", "memberPayments", LINE);
    
    private final String name;
    private final String nested;
    private final ChartType type;
    ChartField(String name, String nested, ChartType type) {
        this.name = name;
        this.nested = nested;
        this.type = type;
    }
    public String getName() { return name; }
    public String getNested() { return nested; }
    public ChartType getType() { return type; }
}
