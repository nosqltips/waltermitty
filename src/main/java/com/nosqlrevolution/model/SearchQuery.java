package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class SearchQuery implements Serializable {    
    private String memberId;
    
    private String timeMillis;
    private Long totalResults;
    private Integer availableResults;
            
    private Integer pageFrom = -1;
    private Integer pageSize = -1;

    private List<Result> results;
    private List<FacetRequest> facets;
    private List<Chart> charts;
    private LineChart lineChart;
    private List<Boost> boosts;
    
    public SearchQuery() {}

    @JsonProperty("memberId")
    public String getMemberId() {
        return memberId;
    }

    @JsonProperty("memberId")
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @JsonProperty("timeMillis")
    public String getTimeMillis() {
        return timeMillis;
    }

    @JsonProperty("timeMillis")
    public void setTimeMillis(String timeMillis) {
        this.timeMillis = timeMillis;
    }

    @JsonProperty("totalResults")
    public Long getTotalResults() {
        return totalResults;
    }

    @JsonProperty("totalResults")
    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    @JsonProperty("availableResults")
    public Integer getAvailableResults() {
        return availableResults;
    }

    @JsonProperty("availableResults")
    public void setAvailableResults(Integer availableResults) {
        this.availableResults = availableResults;
    }

    @JsonProperty("pageFrom")
    public Integer getPageFrom() {
        return pageFrom;
    }

    @JsonProperty("pageFrom")
    public void setPageFrom(Integer pageFrom) {
        this.pageFrom = pageFrom;
    }

    @JsonProperty("pageSize")
    public Integer getPageSize() {
        return pageSize;
    }

    @JsonProperty("pageSize")
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @JsonProperty("results")
    public List<Result> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<Result> results) {
        this.results = results;
    }

    @JsonProperty("facets")
    public List<FacetRequest> getFacets() {
        return facets;
    }

    @JsonProperty("facets")
    public void setFacets(List<FacetRequest> facets) {
        this.facets = facets;
    }

    @JsonProperty("charts")
    public List<Chart> getCharts() {
        return charts;
    }

    @JsonProperty("charts")
    public void setCharts(List<Chart> charts) {
        this.charts = charts;
    }

    @JsonProperty("lineChart")
    public LineChart getLineChart() {
        return lineChart;
    }

    @JsonProperty("lineChart")
    public void setLineChart(LineChart lineChart) {
        this.lineChart = lineChart;
    }

    @JsonProperty("boosts")
    public List<Boost> getBoosts() {
        return boosts;
    }

    @JsonProperty("boosts")
    public void setBoosts(List<Boost> boosts) {
        this.boosts = boosts;
    }
    
    @JsonIgnore
    public void addFacet(FacetRequest facet) {
        if (facets == null) {
            facets = new ArrayList<>();
        }
        facets.add(facet);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("memberId=").append(memberId).append("\n");
        return builder.toString();
    }
}
