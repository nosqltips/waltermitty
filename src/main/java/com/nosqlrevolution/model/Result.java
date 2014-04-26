package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class Result implements Serializable {
    private String docId;
    private Long userId;
    private String text;
    private String source;
    private String createdAt;
    private String screenName;
    
    private float score;
            
    public Result() {}
 
    @JsonProperty("docId")
    public String getDocId() {
        return docId;
    }

    @JsonProperty("docId")
    public void setDocId(String docId) {
        this.docId = docId;
    }

    @JsonProperty("userId")
    public Long getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("screenName")
    public String getScreenName() {
        return screenName;
    }

    @JsonProperty("screenName")
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @JsonProperty("score")
    public float getScore() {
        return score;
    }

    @JsonProperty("score")
    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getUserId() != null ? getUserId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Result)) {
            return false;
        }
        Result other = (Result) object;
        return (this.getUserId() != null || other.getUserId() == null) && (this.getUserId() == null || this.userId.equals(other.getUserId()));
    }

    @Override
    public String toString() {
        return "com.nosqlrevolution.model.DisplayValue[ itemId=" + getUserId() + " ]";
    }
}
