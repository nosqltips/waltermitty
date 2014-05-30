package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author cbrown
 */
@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class StatsValues {
    private String name;
    private Double overallAverage = 0.0D;
    private Double projectedYearEndTotal = 0.0D;
    private List<Double> monthlyAverages;
    
    public StatsValues() { }
    
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public StatsValues setName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("overallAverage")
    public Double getOverallAverage() {
        return overallAverage;
    }

    @JsonProperty("overallAverage")
    public StatsValues setOverallAverage(Double overallAverage) {
        this.overallAverage = overallAverage;
        return this;
    }

    @JsonProperty("projectedYearEndTotal")
    public Double getProjectedYearEndTotal() {
        return projectedYearEndTotal;
    }

    @JsonProperty("projectedYearEndTotal")
    public StatsValues setProjectedYearEndTotal(Double projectedYearEndTotal) {
        this.projectedYearEndTotal = projectedYearEndTotal;
        return this;
    }

    @JsonProperty("monthlyAverages")
    public List<Double> getMonthlyAverages() {
        return monthlyAverages;
    }

    @JsonProperty("monthlyAverages")
    public StatsValues setMonthlyAverages(List<Double> monthlyAverages) {
        this.monthlyAverages = monthlyAverages;
        return this;
    }
}
