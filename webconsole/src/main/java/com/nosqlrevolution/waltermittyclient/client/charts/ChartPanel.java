package com.nosqlrevolution.waltermittyclient.client.charts;


import com.google.gwt.user.client.ui.FlowPanel;
import com.nosqlrevolution.waltermittyclient.model.LineChart;
import com.nosqlrevolution.waltermittyclient.model.SearchQuery;

/**
 * Created by noSqlOrBust on 5/28/2014.
 */
public class ChartPanel extends FlowPanel {

    private FlowPanel panel;

    public ChartPanel() {
        setStyleName("centerPanelChartPanel");

//        BarChartPanel barChartPanel = new BarChartPanel();
//        add(barChartPanel);
//
//        LineChartPanel lineChartPanel = new LineChartPanel();
//        add(lineChartPanel);
    }

    public void update(SearchQuery searchQuery)
    {
        clear();

//        if (searchQuery != null && searchQuery.getCharts() != null) {
//           for (Chart chart : searchQuery.getCharts()) {
//                switch (chart.getChartType()){
//                    case BAR:
//                        BarChartPanel barChartPanel = new BarChartPanel(chart);
//                        add(barChartPanel);
//                        break;
//                    case GEO:
//                        GeoChartPanel geoChartPanel = new GeoChartPanel(chart);
//                        add(geoChartPanel);
//                        break;
//                    case SCATTER:
//                        break;
//                    case LINE:
////                        LineChartPanel lineChartPanel = new LineChartPanel(chart);
////                        add(lineChartPanel);
//                        break;
//                }
//            }
//        }

        if (searchQuery != null && searchQuery.getLineChart() != null) {
            LineChart lineChart = searchQuery.getLineChart();

            LineChartPanel lineChartPanel = new LineChartPanel(lineChart);
            add(lineChartPanel);

        }
    }
}
