package com.nosqlrevolution.waltermittyclient.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cbrown
 */
@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class LineChart implements Serializable {
    private LineChartHeader header;
    private List<LineChartValue> values;
    private Double memberTotalYearContrib;
    private Double memberYearEndBalance;
    private Double memberMonthlyContributionIncrease;
    private Double memberYearlyContributionIncrease;
    private Double groupTotalYearContrib;
    private Double groupYearEndBalance;
    private Double groupMonthlyContributionIncrease;
    private Double groupYearlyContributionIncrease;
    
    public LineChart() { }
    
    @JsonProperty("header")
    public LineChartHeader getHeader() {
        return header;
    }

    @JsonProperty("header")
    public LineChart setHeader(LineChartHeader header) {
        this.header = header;
        return this;
    }

    @JsonProperty("values")
    public List<LineChartValue> getValues() {
        return values;
    }

    @JsonProperty("values")
    public LineChart setValues(List<LineChartValue> values) {
        this.values = values;
        return this;
    }

    @JsonProperty("memberTotalYearContrib")
    public Double getMemberTotalYearContrib() {
        return memberTotalYearContrib;
    }

    @JsonProperty("memberTotalYearContrib")
    public LineChart setMemberTotalYearContrib(Double memberTotalYearContrib) {
        this.memberTotalYearContrib = memberTotalYearContrib;
        return this;
    }

    @JsonProperty("memberYearEndBalance")
    public Double getMemberYearEndBalance() {
        return memberYearEndBalance;
    }

    @JsonProperty("memberYearEndBalance")
    public LineChart setMemberYearEndBalance(Double memberYearEndBalance) {
        this.memberYearEndBalance = memberYearEndBalance;
        return this;
    }

    @JsonProperty("memberMonthlyContributionIncrease")
    public Double getMemberMonthlyContributionIncrease() {
        return memberMonthlyContributionIncrease;
    }

    @JsonProperty("memberMonthlyContributionIncrease")
    public LineChart setMemberMonthlyContributionIncrease(Double memberMonthlyContributionIncrease) {
        this.memberMonthlyContributionIncrease = memberMonthlyContributionIncrease;
        return this;
    }

    @JsonProperty("memberYearlyContributionIncrease")
    public Double getMemberYearlyContributionIncrease() {
        return memberYearlyContributionIncrease;
    }

    @JsonProperty("memberYearlyContributionIncrease")
    public LineChart setMemberYearlyContributionIncrease(Double memberYearlyContributionIncrease) {
        this.memberYearlyContributionIncrease = memberYearlyContributionIncrease;
        return this;
    }

    @JsonProperty("groupTotalYearContrib")
    public Double getGroupTotalYearContrib() {
        return groupTotalYearContrib;
    }

    @JsonProperty("groupTotalYearContrib")
    public LineChart setGroupTotalYearContrib(Double groupTotalYearContrib) {
        this.groupTotalYearContrib = groupTotalYearContrib;
        return this;
    }

    @JsonProperty("groupYearEndBalance")
    public Double getGroupYearEndBalance() {
        return groupYearEndBalance;
    }

    @JsonProperty("groupYearEndBalance")
    public LineChart setGroupYearEndBalance(Double groupYearEndBalance) {
        this.groupYearEndBalance = groupYearEndBalance;
        return this;
    }

    @JsonProperty("groupMonthlyContributionIncrease")
    public Double getGroupMonthlyContributionIncrease() {
        return groupMonthlyContributionIncrease;
    }

    @JsonProperty("groupMonthlyContributionIncrease")
    public LineChart setGroupMonthlyContributionIncrease(Double groupMonthlyContributionIncrease) {
        this.groupMonthlyContributionIncrease = groupMonthlyContributionIncrease;
        return this;
    }

    @JsonProperty("groupYearlyContributionIncrease")
    public Double getGroupYearlyContributionIncrease() {
        return groupYearlyContributionIncrease;
    }

    @JsonProperty("groupYearlyContributionIncrease")
    public LineChart setGroupYearlyContributionIncrease(Double groupYearlyContributionIncrease) {
        this.groupYearlyContributionIncrease = groupYearlyContributionIncrease;
        return this;
    }
}
