package com.nosqlrevolution.waltermittyclient.client.charts;

//import com.google.gwt.core.client.JsArray;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.corechart.BarChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.nosqlrevolution.waltermittyclient.model.Chart;
import com.nosqlrevolution.waltermittyclient.model.ChartValue;
import com.nosqlrevolution.waltermittyclient.model.Result;

/**
 * Created by noSqlOrBust on 5/24/2014.
 */
public class BarChartPanel extends Composite {

    private FlowPanel panel;
    private Chart chart;

    public BarChartPanel() {
//        panel = new FlowPanel();
//        panel.setStyleName("barChartPanel");
//        initWidget(panel);
//
//
//        // Create a callback to be called when the visualization API
//        // has been loaded.
//        Runnable onLoadCallback = new Runnable() {
//            public void run() {
//                // Create a BarChart chart visualization.
//                BarChart bar = new BarChart(createTable(), createOptions());
//
//                bar.addSelectHandler(createSelectHandler(bar));
//                panel.add(bar);
//            }
//        };
//
//        // Load the visualization api, passing the onLoadCallback to be called
//        // when loading is done.
//        VisualizationUtils.loadVisualizationApi(onLoadCallback, BarChart.PACKAGE);
    }

    public BarChartPanel(Chart chart) {
        this.chart = chart;

        panel = new FlowPanel();
        panel.setStyleName("barChartPanel");
        initWidget(panel);



//
        // Create a callback to be called when the visualization API
        // has been loaded.
        Runnable onLoadCallback = new Runnable() {
            public void run() {
                // Create a BarChart chart visualization.
                AbstractDataTable chartDataTable = createChartDataTable();
                BarChart bar = new BarChart(chartDataTable, createOptions());
                bar.setTitle(BarChartPanel.this.chart.getName());

                bar.addSelectHandler(createSelectHandler(bar));
                panel.add(bar);
            }
        };

        // Load the visualization api, passing the onLoadCallback to be called
        // when loading is done.
        VisualizationUtils.loadVisualizationApi(onLoadCallback, BarChart.PACKAGE);
    }

    private Options createOptions() {
        Options options = Options.create();
        options.setWidth(300);
        options.setHeight(180);
//        options.set3D(true);
        options.setTitle(chart.getName());
        return options;
    }

    private SelectHandler createSelectHandler(final BarChart chart) {
        return new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                String message = "";

                // May be multiple selections.
                JsArray<Selection> selections = chart.getSelections();

                for (int i = 0; i < selections.length(); i++) {
                    // add a new line for each selection
                    message += i == 0 ? "" : "\n";

                    Selection selection = selections.get(i);

                    if (selection.isCell()) {
                        // isCell() returns true if a cell has been selected.

                        // getRow() returns the row number of the selected cell.
                        int row = selection.getRow();
                        // getColumn() returns the column number of the selected cell.
                        int column = selection.getColumn();
                        message += "cell " + row + ":" + column + " selected";
                    } else if (selection.isRow()) {
                        // isRow() returns true if an entire row has been selected.

                        // getRow() returns the row number of the selected row.
                        int row = selection.getRow();
                        message += "row " + row + " selected";
                    } else {
                        // unreachable
                        message += "Bar chart selections should be either row selections or cell selections.";
                        message += "  Other visualizations support column selections as well.";
                    }
                }

                Window.alert(message);
            }
        };
    }

//    private AbstractDataTable createTable() {
//        DataTable data = DataTable.create();
//        data.addColumn(AbstractDataTable.ColumnType.STRING, "Task");
//        data.addColumn(AbstractDataTable.ColumnType.NUMBER, "Hours per Day");
//        data.addRows(2);
//        data.setValue(0, 0, "Work");
//        data.setValue(0, 1, 14);
//        data.setValue(1, 0, "Sleep");
//        data.setValue(1, 1, 10);
//        return data;
//    }

    private AbstractDataTable createChartDataTable() {
        DataTable data = DataTable.create();

        data.addColumn(AbstractDataTable.ColumnType.STRING, chart.getName());
        data.addColumn(AbstractDataTable.ColumnType.NUMBER, "Count");
        data.addRows(chart.getChartValues().size());

        int row = 0;
        for (ChartValue chartValue : chart.getChartValues())
        {
            data.setValue(row, 0, chartValue.getName());
            data.setValue(row, 1, chartValue.getCount().doubleValue());
            row++;
        }
        return data;
    }

    public void update(Result result)
    {

    }
}
