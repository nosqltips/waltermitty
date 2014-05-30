package com.nosqlrevolution.waltermittyclient.client.charts;


import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Created by noSqlOrBust on 5/28/2014.
 */
public class ChartPanel extends FlowPanel {

    private FlowPanel panel;

    public ChartPanel() {
        setStyleName("centerPanelChartPanel");

        BarChartPanel barChartPanel = new BarChartPanel();
        add(barChartPanel);

        LineChartPanel lineChartPanel = new LineChartPanel();
        add(lineChartPanel);
    }
}
