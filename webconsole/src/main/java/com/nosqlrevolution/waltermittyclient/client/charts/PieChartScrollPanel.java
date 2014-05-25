package com.nosqlrevolution.waltermittyclient.client.charts;

import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 */
public class PieChartScrollPanel extends LazyPanel {
    public PieChartScrollPanel() {
        addStyleName("pieChartScrollPanel");
//        add(new PieChartPanel());
    }

    @Override
    protected Widget createWidget() {
        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.add(new PieChartPanel());

        return scrollPanel;
    }
}
