package com.nosqlrevolution.waltermittyclient.client.widgets;

import com.google.gwt.widgetideas.client.SliderBar;
import com.nosqlrevolution.waltermittyclient.model.Boost;

/**
 *
 */
public class MittySlider extends SliderBar {
    Boost boost;

    public MittySlider(double minValue, double maxValue, Boost boost) {
        super(minValue, maxValue);

        this.boost = boost;

        setStepSize(0.1);
        setNumTicks(16);
        setNumLabels(6);
        setCurrentValue(boost.getBoost());
        setTitle(boost.getDisplay());
    }

    public void update(Boost boost)
    {
        this.boost = boost;
        setCurrentValue(boost.getBoost());
        setTitle(boost.getDisplay());
    }
}
