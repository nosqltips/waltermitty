package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.nosqlrevolution.waltermittyclient.model.Result;
import com.nosqlrevolution.waltermittyclient.client.cmd.GetRestCmd;
import com.nosqlrevolution.waltermittyclient.client.widgets.MittyClickableCellText;
import com.nosqlrevolution.waltermittyclient.client.widgets.PleaseWaitWidget;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author noSqlOrBust
 */
public class CenterPanel extends FlowPanel
{
    private List<Result> results = new ArrayList<>();
    private ListDataProvider<Result> dataProvider;
    private CellTable<Result> table;
    private PleaseWaitWidget pleaseWaitWidget;
    private GetRestCmd restGetCmd;


    public CenterPanel() {
        setStyleName("centerPanel");
    }

    public CenterPanel(GetRestCmd restGetCmd)
    {
        this.restGetCmd = restGetCmd;
        setStyleName("centerPanel");

        // Create a CellTable.
        table = new CellTable<Result>();
        table.setStyleName("centerPanelResultsTable");

        Column<Result, String> nameColumn = new Column<Result, String>(new MittyClickableCellText()){
            @Override
            public String getValue(Result obj){
                return obj.getMemberId();
            }
        };

        nameColumn.setFieldUpdater(new FieldUpdater<Result, String>(){
            @Override
            public void update(int index, Result obj, String value){
//                Window.alert("You clicked " + obj.getMemberId());
                CenterPanel.this.restGetCmd.setMemberId(obj.getMemberId());
                CenterPanel.this.restGetCmd.execute();
            }
        });

        // Create state column.
        TextColumn<Result> stateColumn = new TextColumn<Result>() {
            @Override
            public String getValue(Result result) {
                return result.getState();
            }
        };

        // Create zip column.
        TextColumn<Result> zipColumn = new TextColumn<Result>() {
            @Override
            public String getValue(Result result) {
                return result.getZip();
            }
        };

        // Create birthyear column.
        TextColumn<Result> birthYearColumn = new TextColumn<Result>() {
            @Override
            public String getValue(Result result) {
                return result.getBirthYear();
            }
        };

        // Create gender column.
        TextColumn<Result> genderColumn = new TextColumn<Result>() {
            @Override
            public String getValue(Result result) {
                return result.getGender();
            }
        };

        // Create numDependents column.
        TextColumn<Result> numDependentsColumn = new TextColumn<Result>() {
            @Override
            public String getValue(Result result) {
                return result.getNumDependents().toString();
            }
        };

        // Create numPayments column.
        TextColumn<Result> numPaymentsColumn = new TextColumn<Result>() {
            @Override
            public String getValue(Result result) {
                return result.getNumPayments().toString();
            }
        };

        // Create numClaims column.
        TextColumn<Result> numClaimsColumn = new TextColumn<Result>() {
            @Override
            public String getValue(Result result) {
                return result.getNumClaims().toString();
            }
        };

        // Create score column.
        TextColumn<Result> scoreColumn = new TextColumn<Result>() {
            @Override
            public String getValue(Result result) {
                return Float.toString(result.getScore());
            }
        };

        // Add the columns.
        table.addColumn(nameColumn, "Member Id");
        table.addColumn(birthYearColumn, "Birth Year");
        table.addColumn(genderColumn, "Gender");
        table.addColumn(numDependentsColumn, "# Dependents");
        table.addColumn(numPaymentsColumn, "# Payments");
        table.addColumn(numClaimsColumn, "# Claims");
        table.addColumn(stateColumn, "State");
        table.addColumn(zipColumn, "ZIP");
        table.addColumn(scoreColumn, "Score");

        // Create a data provider.
        dataProvider = new ListDataProvider<Result>();

        // Connect the table to the data provider.
        dataProvider.addDataDisplay(table);

        // Add the data to the data provider, which automatically pushes it to the
        // widget.
        List<Result> list = dataProvider.getList();
        for (Result result : results) {
            list.add(result);
        }

        // Add a ColumnSortEvent.ListHandler to connect sorting to the
        // java.util.List.
        ColumnSortEvent.ListHandler<Result> columnSortHandler = new ColumnSortEvent.ListHandler<Result>(
                list);
        columnSortHandler.setComparator(nameColumn,
                new Comparator<Result>() {
                    public int compare(Result o1, Result o2) {
                        if (o1 == o2) {
                            return 0;
                        }

                        // Compare the name columns.
                        if (o1 != null) {
                            return (o2 != null) ? o1.getMemberId().compareTo(o2.getMemberId()) : 1;
                        }
                        return -1;
                    }
                }
        );
        table.addColumnSortHandler(columnSortHandler);

        // We know that the data is sorted alphabetically by default.
        table.getColumnSortList().push(nameColumn);

        table.addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
            @Override
            public void onRowCountChange(RowCountChangeEvent event) {
                table.setVisibleRange(new Range(0, event.getNewRowCount()));
            }
        });
        // Add it to the root panel.
        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setStyleName("centerPanelScrollPanel");
        scrollPanel.add(table);

        add(scrollPanel);
    }

    public void update(List<Result> results)
    {
        showPleaseWait(true);

        this.results = results;

        dataProvider.flush();
        dataProvider.setList(results);
        dataProvider.refresh();

        showPleaseWait(false);
    }

    public void showPleaseWait(boolean show)
    {
        if (pleaseWaitWidget == null)
            pleaseWaitWidget = new PleaseWaitWidget(null);

        if (show)
        {

            pleaseWaitWidget.setPopupPositionAndShow(new PopupPanel.PositionCallback()
            {
                @Override
                public void setPosition(int offsetWidth, int offsetHeight)
                {
                    int left = CenterPanel.this.getAbsoluteLeft() + CenterPanel.this.getOffsetWidth() / 2 - offsetWidth / 2;
                    int top = CenterPanel.this.getAbsoluteTop() + CenterPanel.this.getOffsetHeight() / 2 - offsetHeight / 2;
//                    int left = Windows.this.getAbsoluteLeft() + CenterPanel.this.getOffsetWidth() / 2 - offsetWidth / 2;
//                    int top = CenterPanel.this.getAbsoluteTop() + CenterPanel.this.getOffsetHeight() / 2 - offsetHeight / 2;
                    pleaseWaitWidget.setPopupPosition(left, top);
                }
            });
            pleaseWaitWidget.setGlassEnabled(true);
        }
        else
        {
            pleaseWaitWidget.hide(true);
        }
    }
}
