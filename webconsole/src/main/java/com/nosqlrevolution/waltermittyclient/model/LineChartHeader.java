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
public class LineChartHeader implements Serializable {
    private String name;
    private List<String> values;
    
    public LineChartHeader() { }
    
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public LineChartHeader setName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("values")
    public List<String> getValues() {
        return values;
    }

    @JsonProperty("values")
    public LineChartHeader setValues(List<String> values) {
        this.values = values;
        return this;
    }
}
