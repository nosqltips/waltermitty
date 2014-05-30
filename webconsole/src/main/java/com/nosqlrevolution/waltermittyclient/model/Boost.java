package com.nosqlrevolution.waltermittyclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nosqlrevolution.waltermittyclient.enums.BoostField;

/**
 *
 * @author cbrown
 */
public class Boost {
    private BoostField field;
    private String display;
    private Float boost;

    public Boost() {}
    public Boost(BoostField field) {
        this.field = field;
        this.boost = field.getDefaultBoost();
        this.display = field.getDisplay();
    }
    
    @JsonProperty("field")
    public BoostField getField() {
        return field;
    }

    @JsonProperty("field")
    public void setField(BoostField field) {
        this.field = field;
    }
    
    @JsonProperty("display")
    public String getDisplay() {
        return display;
    }

    @JsonProperty("display")
    public void setDisplay(String display) {
        this.display = display;
    }

    @JsonProperty("boost")
    public Float getBoost() {
        return boost;
    }

    @JsonProperty("boost")
    public void setBoost(Float boost) {
        this.boost = boost;
    }
}
