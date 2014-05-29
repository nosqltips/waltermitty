package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nosqlrevolution.enums.ChartField;
import com.nosqlrevolution.enums.ChartType;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cbrown
 */
@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class Chart implements Comparable<Chart>, Serializable {
    private String name;
    private ChartField chartField;
    private ChartType chartType;
    private List<ChartValue> chartValues;
    
    public Chart() { }
    
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public Chart setName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("chartField")
    public ChartField getField() {
        return chartField;
    }

    @JsonProperty("chartField")
    public Chart setField(ChartField chartField) {
        this.chartField = chartField;
        return this;
    }

    @JsonProperty("chartType")
    public ChartType getType() {
        return chartType;
    }

    @JsonProperty("chartType")
    public Chart setType(ChartType chartType) {
        this.chartType = chartType;
        return this;
    }

    @JsonProperty("chartValues")
    public List<ChartValue> getValues() {
        return chartValues;
    }

    @JsonProperty("chartValues")
    public Chart setValues(List<ChartValue> chartValues) {
        this.chartValues = chartValues;
        return this;
    }
    
    @Override
    public int compareTo(Chart o) {
        return (o.getName().compareTo(name));
    }
}
