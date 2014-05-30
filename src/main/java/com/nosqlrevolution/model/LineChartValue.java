package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cbrown
 */
@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class LineChartValue implements Serializable {
    private String name;
    private List<Double> values;
    
    public LineChartValue() { }
    
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public LineChartValue setName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("values")
    public List<Double> getValues() {
        return values;
    }

    @JsonProperty("values")
    public LineChartValue setValues(List<Double> values) {
        this.values = values;
        return this;
    }
}
