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
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.ScatterChart;

import java.util.Random;

/**
 * Created by noSqlOrBust on 5/24/2014.
 */
public class ScatterChartPanel extends Composite {

    private final FlowPanel panel;

    public ScatterChartPanel() {
        panel = new FlowPanel();
        initWidget(panel);

        
        // Create a callback to be called when the visualization API
        // has been loaded.
        Runnable onLoadCallback = new Runnable() {
            public void run() {

                // Create a Scatter chart visualization.
                ScatterChart scatter = new ScatterChart(createScatterTable(), createOptions());

                scatter.addSelectHandler(createSelectHandler(scatter));
                panel.add(scatter);
            }
        };

        // Load the visualization api, passing the onLoadCallback to be called
        // when loading is done.
        VisualizationUtils.loadVisualizationApi(onLoadCallback, ScatterChart.PACKAGE);
    }

    private Options createOptions() {
        Options options = Options.create();
        options.setWidth(800);
        options.setHeight(480);
//        options.set3D(true);
        options.setTitle("Cool Widgets");
//        options.set("trendlines", "0");

        options.setHAxisOptions(createHAxisOptions());
        options.setVAxisOptions(createVAxisOptions());

        return options;
    }

    private AxisOptions createHAxisOptions() {
        AxisOptions options = AxisOptions.create();
        options.setTitle("Y");
        options.setBaselineColor("green");
        options.set("trendlines", "0");

        return options;
    }

    private AxisOptions createVAxisOptions() {
        AxisOptions options = AxisOptions.create();
        options.setTitle("X");
        options.setBaselineColor("green");

        return options;
    }

    private Options createOptionsTrend() {
        Options options = Options.create();
        options.setWidth(800);
        options.setHeight(480);
//        options.set3D(true);
        options.setTitle("Cool Widgets");
        options.setHAxisOptions(createHAxisOptions());
        options.setVAxisOptions(createVAxisOptions());
        options.set("trendlines", "0");

        return options;
    }

    private SelectHandler createSelectHandler(final ScatterChart chart) {
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
                        message += "Scatter chart selections should be either row selections or cell selections.";
                        message += "  Other visualizations support column selections as well.";
                    }
                }

                Window.alert(message);
            }
        };
    }

    private AbstractDataTable createScatterTable() {
        int numRows = 20;

        DataTable data = DataTable.create();
        data.addColumn(AbstractDataTable.ColumnType.NUMBER, "X");
        data.addColumn(AbstractDataTable.ColumnType.NUMBER, "Widget 1");
        data.addColumn(AbstractDataTable.ColumnType.NUMBER, "Widget 2");
        data.addRows(numRows);

        Random randomGenerator = new Random();
        for (int i=0; i<numRows; i++)
        {
            double sinResult = Math.sin(i / 5) * 0.25;
            double cosResult = Math.cos(i / 25);
            data.setValue(i, 0, randomGenerator.nextInt(100));
            data.setValue(i, 1, sinResult);
            data.setValue(i, 2, cosResult);
        }

        for (int i=0; i<numRows; i++)
        {
            double sinResult = Math.sin(i / 25);
            double cosResult = Math.cos(i / 10) * 0.5;
            data.setValue(i, 0, randomGenerator.nextInt(100));
            data.setValue(i, 1, sinResult);
            data.setValue(i, 2, cosResult);
        }

//        DataTable data = DataTable.create();
//        data.addColumn(AbstractDataTable.ColumnType.NUMBER, "Sugar");
//        data.addColumn(AbstractDataTable.ColumnType.NUMBER, "Calories");
//        data.addColumn(AbstractDataTable.ColumnType.NUMBER, "Salt");
//        data.addRows(4);
//        data.setValue(0, 0, 20);
//        data.setValue(0, 1, 11);
//        data.setValue(0, 2, 30);
//        data.setValue(1, 0, 30);
//        data.setValue(1, 1, 10);
//        data.setValue(1, 2, 20);
//        data.setValue(2, 0, 27);
//        data.setValue(2, 1, 2);
//        data.setValue(2, 2, 34);
//        data.setValue(3, 0, 22);
//        data.setValue(3, 1, 2);
//        data.setValue(3, 1, 9);

        return data;
    }
}
