package com.nosqlrevolution.waltermittyclient.client.charts;

import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 */
public class LineChartScrollPanel extends LazyPanel {
    public LineChartScrollPanel() {
        addStyleName("lineChartScrollPanel");
        add(new LineChartPanel());
    }

    @Override
    protected Widget createWidget() {
        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.add(new BarChartPanel());

        return scrollPanel;
    }
}
