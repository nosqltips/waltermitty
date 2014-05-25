package com.nosqlrevolution.waltermittyclient.client.charts;

import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 */
public class BarChartScrollPanel extends LazyPanel {
    public BarChartScrollPanel() {
        setStyleName("barChartScrollPanel");
//        add(new BarChartPanel());
    }

    @Override
    protected Widget createWidget() {
        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.add(new BarChartPanel());

        return scrollPanel;
    }
}
