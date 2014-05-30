package com.nosqlrevolution.waltermittyclient.client.charts;

//import com.google.gwt.core.client.JsArray;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.nosqlrevolution.waltermittyclient.model.LineChartValue;
import com.nosqlrevolution.waltermittyclient.model.Result;

import java.util.List;

/**
 * Created by noSqlOrBust on 5/30/2014.
 */
public class LineChartPanel extends Composite {
    private HorizontalPanel hp;
    private FlowPanel infoPanel;
    private FlowPanel panel;
    private com.nosqlrevolution.waltermittyclient.model.LineChart lineChartModel;

    public LineChartPanel() {
//        panel = new FlowPanel();
//        panel.setStyleName("lineChartPanel");
//        initWidget(panel);
//
//
//        // Create a callback to be called when the visualization API
//        // has been loaded.
//        Runnable onLoadCallback = new Runnable() {
//            public void run() {
//                // Create a Line chart visualization.
//                LineChart line = new LineChart(createTable(), createOptions());
//
//                line.addSelectHandler(createSelectHandler(line));
//                panel.add(line);
//            }
//        };
//
//        // Load the visualization api, passing the onLoadCallback to be called
//        // when loading is done.
//        VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
    }

    public LineChartPanel(com.nosqlrevolution.waltermittyclient.model.LineChart lineChart) {
        this.lineChartModel = lineChart;

        hp = new HorizontalPanel();
        panel = new FlowPanel();
        panel.setStyleName("barChartPanel");
        hp.add(panel);

        infoPanel = new FlowPanel();
        hp.add(infoPanel);

        initWidget(hp);

        infoPanel.add(new Label(lineChart.getMemberMonthlyContributionIncrease().toString()));
        infoPanel.add(new Label(lineChart.getGroupTotalYearContrib().toString()));
        infoPanel.add(new Label(lineChart.getGroupYearEndBalance().toString()));
        infoPanel.add(new Label(lineChart.getGroupYearlyContributionIncrease().toString()));
        infoPanel.add(new Label(lineChart.getMemberMonthlyContributionIncrease().toString()));
        infoPanel.add(new Label(lineChart.getMemberTotalYearContrib().toString()));
        infoPanel.add(new Label(lineChart.getMemberYearEndBalance().toString()));
        infoPanel.add(new Label(lineChart.getMemberYearlyContributionIncrease().toString()));

//
        // Create a callback to be called when the visualization API
        // has been loaded.
        Runnable onLoadCallback = new Runnable() {
            public void run() {
                // Create a BarChart chart visualization.
                AbstractDataTable chartDataTable = createChartDataTable();
                LineChart lineChart = new LineChart(chartDataTable, createOptions());
                lineChart.setTitle(LineChartPanel.this.lineChartModel.getHeader().getName());

                lineChart.addSelectHandler(createSelectHandler(lineChart));
                panel.add(lineChart);
            }
        };

        // Load the visualization api, passing the onLoadCallback to be called
        // when loading is done.
        VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
    }

    private Options createOptions() {
        Options options = Options.create();
        options.setWidth(800);
        options.setHeight(240);
//        options.set3D(true);
//        options.setTitle("My Daily Activities");
        return options;
    }

    private AbstractDataTable createChartDataTable() {
        DataTable data = DataTable.create();
        if (lineChartModel.getHeader().getValues() == null)
        {
            return data;
        }

        data.addColumn(AbstractDataTable.ColumnType.STRING, lineChartModel.getHeader().getName());
        for (LineChartValue lineChartValue: lineChartModel.getValues()) {
            data.addColumn(AbstractDataTable.ColumnType.NUMBER, lineChartValue.getName());
        }

        data.addRows(lineChartModel.getHeader().getValues().size());

        int row = 0;
        int specialCount = 0;
        List<LineChartValue> rowData = lineChartModel.getValues();
        for (String name: lineChartModel.getHeader().getValues()) {
            data.setValue(row, 0, name);

            int column = 1;
            for (LineChartValue lineChartValue: lineChartModel.getValues()) {
                if (row < lineChartValue.getValues().size())
                {
                    data.setValue(row, column, lineChartValue.getValues().get(row));
                    column++;
                }
            }
            row++;
        }
        return data;
    }

    private AbstractDataTable createTable() {
        DataTable data = DataTable.create();
        data.addColumn(AbstractDataTable.ColumnType.STRING, "Task");
        data.addColumn(AbstractDataTable.ColumnType.NUMBER, "Hours per Day");
        data.addRows(2);
        data.setValue(0, 0, "Work");
        data.setValue(0, 1, 14);
        data.setValue(1, 0, "Sleep");
        data.setValue(1, 1, 10);
        return data;
    }

    public void update(Result result)
    {

    }

    private SelectHandler createSelectHandler(final LineChart chart) {
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
                        message += "Line chart selections should be either row selections or cell selections.";
                        message += "  Other visualizations support column selections as well.";
                    }
                }

                Window.alert(message);
            }
        };
    }

}
