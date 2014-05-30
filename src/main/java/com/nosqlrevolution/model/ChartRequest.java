package com.nosqlrevolution.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nosqlrevolution.enums.ChartField;
import java.io.Serializable;

/**
 *
 * @author cbrown
 */
@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class ChartRequest implements Serializable {
    private ChartField field;
    private int size = 10;
    
    public ChartRequest() {}

    public ChartRequest(ChartField field) {
        this.field = field;
    }

    public ChartRequest(ChartField field, int size) {
        this.field = field;
        this.size = size;
    }
    
    @JsonProperty("field")
    public ChartField getField() {
        return field;
    }

    @JsonProperty("field")
    public ChartRequest setField(ChartField field) {
        this.field = field;
        return this;
    }

    @JsonIgnore
    public ChartRequest setField(String fieldName) {
        this.field = ChartField.valueOf(fieldName);
        return this;
    }

    @JsonProperty("size")
    public int getSize() {
        return size;
    }

    @JsonProperty("size")
    public ChartRequest setSize(int size) {
        this.size = size;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj instanceof ChartRequest) {
            ChartRequest other = (ChartRequest) obj;
            if (this.getField().equals(other.getField())) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
