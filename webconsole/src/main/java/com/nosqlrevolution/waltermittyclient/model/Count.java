package com.nosqlrevolution.waltermittyclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Count {
    private long count;

    @JsonProperty("count")
    public long getCount() {
        return count;
    }

    @JsonProperty("count")
    public Count setCount(long count) {
        this.count = count;
        return this;
    }
}
