package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class SearchQuery implements Serializable {    
    private String terms;
    private String screenName;
    private String docId;
    private String beginAt;
    
    private String timeMillis;
    private long totalResults;
    private int availableResults;
            
    private int page = 1;
    private int pageFrom = -1;
    private int pageSize = -1;
    
    public SearchQuery() {}

    @JsonProperty("terms")
    public String getTerms() {
        return terms;
    }

    @JsonProperty("terms")
    public void setTerms(String terms) {
        this.terms = terms;
    }

    @JsonProperty("screenName")
    public String getScreenName() {
        return screenName;
    }

    @JsonProperty("screenName")
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @JsonProperty("docId")
    public String getDocId() {
        return docId;
    }

    @JsonProperty("docId")
    public void setDocId(String docId) {
        this.docId = docId;
    }

    @JsonProperty("beginAt")
    public String getBeginAt() {
        return beginAt;
    }

    @JsonProperty("beginAt")
    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
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
    public long getTotalResults() {
        return totalResults;
    }

    @JsonProperty("totalResults")
    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    @JsonProperty("availableResults")
    public int getAvailableResults() {
        return availableResults;
    }

    @JsonProperty("availableResults")
    public void setAvailableResults(int availableResults) {
        this.availableResults = availableResults;
    }

    @JsonProperty("page")
    public int getPage() {
        return page;
    }

    @JsonProperty("page")
    public void setPage(int page) {
        this.page = page;
    }

    @JsonProperty("pageFrom")
    public int getPageFrom() {
        return pageFrom;
    }

    @JsonProperty("pageFrom")
    public void setPageFrom(int pageFrom) {
        this.pageFrom = pageFrom;
    }

    @JsonProperty("pageSize")
    public int getPageSize() {
        return pageSize;
    }

    @JsonProperty("pageSize")
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("terms=").append(terms).append(" ");
        builder.append("screenName=").append(screenName).append(" ");
        builder.append("id=").append(docId).append("\n");
        return builder.toString();
    }
}
