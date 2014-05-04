package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author cbrown
 */
public class SelectableFacet implements Comparable<SelectableFacet> {
    private String name;
    private long count;
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
    public long getCount() {
        return count;
    }

    @JsonProperty("count")
    public SelectableFacet setCount(long count) {
        this.count = count;
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
