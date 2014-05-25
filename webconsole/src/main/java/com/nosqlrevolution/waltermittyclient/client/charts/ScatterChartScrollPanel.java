package com.nosqlrevolution.waltermittyclient.client.charts;

import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 */
public class ScatterChartScrollPanel extends LazyPanel {
    public ScatterChartScrollPanel() {
        addStyleName("scatterChartScrollPanel");
        add(new ScatterChartPanel());
    }

    @Override
    protected Widget createWidget() {
        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.add(new BarChartPanel());

        return scrollPanel;
    }
}
