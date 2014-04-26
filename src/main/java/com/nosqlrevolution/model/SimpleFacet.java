package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author cbrown
 */
public class SimpleFacet implements Comparable<SimpleFacet> {
    private String name;
    private Integer count;

    public SimpleFacet() { }
    
    public SimpleFacet(String name, Integer count) {
        this.name = name;
        this.count = count;
    }
    
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }
    
    @Override
    public int compareTo(SimpleFacet o) {
//        return (count.compareTo(o.getCount()));
        return (o.getCount().compareTo(count));
    }
}
