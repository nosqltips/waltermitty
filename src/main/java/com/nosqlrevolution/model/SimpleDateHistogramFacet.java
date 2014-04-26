package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nosqlrevolution.util.DateUtil;

/**
 *
 * @author cbrown
 */
public class SimpleDateHistogramFacet {
    private long time;
    private long count;

    public SimpleDateHistogramFacet() { }
    
    public SimpleDateHistogramFacet(long time, long count) {
        this.time = time;
        this.count = count;
    }
    
    @JsonProperty("time")
    public long getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(long time) {
        this.time = time;
    }

    @JsonProperty("relativeTime")
    public String getRelativeTime() {
        return DateUtil.formatRelativeTime(time);
    }

    @JsonProperty("relativeTime")
    public void setRelativeTime(String dummy) { }

    @JsonProperty("count")
    public long getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(long value) {
        this.count = value;
    }
}
