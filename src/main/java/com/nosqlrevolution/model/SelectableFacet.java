package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author cbrown
 */
@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class SelectableFacet implements Comparable<SelectableFacet>, Serializable {
    private String name;
    private Long count;
    private Double value;
    private Boolean selected = Boolean.FALSE;
    
    public SelectableFacet() { }
    
    public SelectableFacet(String name, long count) {
        this.name = name;
        this.count = count;
    }
    
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public SelectableFacet setName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("count")
    public Long getCount() {
        return count;
    }

    @JsonProperty("count")
    public SelectableFacet setCount(Long count) {
        this.count = count;
        return this;
    }

    @JsonProperty("value")
    public Double getValue() {
        return value;
    }

    @JsonProperty("value")
    public SelectableFacet setValue(Double value) {
        this.value = value;
        return this;
    }

    @JsonProperty("selected")
    public Boolean isSelected() {
        return selected;
    }

    @JsonProperty("selected")
    public SelectableFacet setSelected(Boolean selected) {
        this.selected = selected;
        return this;
    }
    
    @Override
    public int compareTo(SelectableFacet o) {
        return (o.getName().compareTo(name));
    }
}
