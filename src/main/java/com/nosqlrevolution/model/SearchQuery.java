package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class SearchQuery implements Serializable {    
    private String memberId;
    
    private String timeMillis;
    private Long totalResults;
    private Integer availableResults;
            
    private Integer pageFrom = -1;
    private Integer pageSize = -1;

    private List<Result> results;
    private List<FacetRequest> facets;
    
    public SearchQuery() {}
    public SearchQuery(List<Result> results, List<FacetRequest> facets) {
        this.results = results;
        this.facets = facets;
    }

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
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("memberId=").append(memberId).append("\n");
        return builder.toString();
    }
}
