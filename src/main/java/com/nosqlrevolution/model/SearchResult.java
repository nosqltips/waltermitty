package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cbrown
 */
public class SearchResult {
    private SearchQuery query;
    private List<Result> results;
    private Map<String, List<SimpleFacet>> facets;
    
    public SearchResult() {}
    public SearchResult(SearchQuery query, List<Result> results) {
        this.query = query;
        this.results = results;
    }
    public SearchResult(SearchQuery query, List<Result> results, Map<String, List<SimpleFacet>> facets) {
        this.query = query;
        this.results = results;
        this.facets = facets;
    }
    
    @JsonProperty("query")
    public SearchQuery getQuery() {
        return query;
    }

    @JsonProperty("query")
    public void setQuery(SearchQuery query) {
        this.query = query;
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
    public Map<String, List<SimpleFacet>> getFacets() {
        return facets;
    }

    @JsonProperty("facets")
    public void setFacets(Map<String, List<SimpleFacet>> facets) {
        this.facets = facets;
    }
}
