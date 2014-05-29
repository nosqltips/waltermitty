package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author cbrown
 */
@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class ChartValue implements Comparable<ChartValue> {
    private String name;
    private Long count;
    private Double value;
    private List<ChartValue> chartValues;
    
    public ChartValue() { }
    
    public ChartValue(String name, Long count) {
        this.name = name;
        this.count = count;
    }
    
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public ChartValue setName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("count")
    public Long getCount() {
        return count;
    }

    @JsonProperty("count")
    public ChartValue setCount(Long count) {
        this.count = count;
        return this;
    }

    @JsonProperty("value")
    public Double getValue() {
        return value;
    }

    @JsonProperty("value")
    public ChartValue setValue(Double value) {
        this.value = value;
        return this;
    }

    @JsonProperty("chartValues")
    public List<ChartValue> getChartValues() {
        return chartValues;
    }

    @JsonProperty("chartValues")
    public ChartValue setChartValues(List<ChartValue> chartValues) {
        this.chartValues = chartValues;
        return this;
    }
    
    @Override
    public int compareTo(ChartValue o) {
        return (o.getName().compareTo(name));
    }
}
