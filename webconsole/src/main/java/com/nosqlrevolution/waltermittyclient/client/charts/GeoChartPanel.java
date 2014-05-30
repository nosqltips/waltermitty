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
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.GeoMap.Options;
import com.nosqlrevolution.waltermittyclient.model.Chart;
import com.nosqlrevolution.waltermittyclient.model.Result;

//import com.google.gwt.visualization.client.visualizations.corechart.BarChart;

/**
 * Created by noSqlOrBust on 5/29/2014.
 */
public class GeoChartPanel extends Composite {

    private FlowPanel panel;
    private Chart chart;

    public GeoChartPanel() {
    }

    public GeoChartPanel(Chart chart) {
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
                GeoMap geo = new GeoMap(chartDataTable, createOptions());
//
//                bar.addSelectHandler(createSelectHandler(bar));
                panel.add(geo);
            }
        };

        // Load the visualization api, passing the onLoadCallback to be called
        // when loading is done.
        VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
    }

    private Options createOptions() {
        Options options = Options.create();
//        options.setWidth(400);
//        options.setHeight(240);
//        options.set3D(true);
//        options.setTitle(chart.getName());

        options.setDataMode(GeoMap.DataMode.MARKERS);
        options.setHeight(240);
        options.setWidth(450);
        options.setShowLegend(false);
        //options.setColors(0xFF8747, 0xFFB581, 0xc06000);
        options.setRegion("US");
        return options;
    }

    private SelectHandler createSelectHandler(final GeoMap chart) {
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
                        message += "Geo chart selections should be either row selections or cell selections.";
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

        DataTable dataTable = DataTable.create();

//        dataTable.addRows(7);
//        dataTable.addColumn(AbstractDataTable.ColumnType.STRING, "ADDRESS", "address");
//        dataTable.setValue(0, 0, "Israel");
//        dataTable.setValue(1, 0, "United States");
//        dataTable.setValue(2, 0, "Germany");
//        dataTable.setValue(3, 0, "Brazil");
//        dataTable.setValue(4, 0, "Canada");
//        dataTable.setValue(5, 0, "France");
//        dataTable.setValue(6, 0, "RU");

        dataTable.addColumn(AbstractDataTable.ColumnType.STRING, "City");
        dataTable.addColumn(AbstractDataTable.ColumnType.NUMBER, "Popularity");
        dataTable.addRows(6);
        dataTable.setValue(0, 0, "New York");
        dataTable.setValue(0, 1, 200);
        dataTable.setValue(1, 0, "Boston");
        dataTable.setValue(1, 1, 300);
        dataTable.setValue(2, 0, "Miami");
        dataTable.setValue(2, 1, 400);
        dataTable.setValue(3, 0, "Chicago");
        dataTable.setValue(3, 1, 500);
        dataTable.setValue(4, 0, "Los Angeles");
        dataTable.setValue(4, 1, 600);
        dataTable.setValue(5, 0, "Houston");
        dataTable.setValue(5, 1, 700);

        return dataTable;
    }

    public void update(Result result)
    {

    }
}
