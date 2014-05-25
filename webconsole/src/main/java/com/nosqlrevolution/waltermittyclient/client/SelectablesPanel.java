package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.nosqlrevolution.waltermittyclient.client.cmd.PostRestCmd;
import com.nosqlrevolution.waltermittyclient.model.SelectableFacet;

/**
 *
 * @author noSqlOrBust
 */
public class SelectablesPanel extends FlowPanel implements ClickHandler
{
    private final SelectableFacet selectableFacet;
    private final PostRestCmd postCmd;
    CheckBox checkbox = new CheckBox();

    public SelectablesPanel(SelectableFacet selectableFacet, PostRestCmd postCmd) {
        this.selectableFacet = selectableFacet;
        this.postCmd = postCmd;

        setStyleName("selectablePanel");
        checkbox.setStyleName("selectablePanelCheckBox");

        // Set label
        if (selectableFacet.getName() != null && !selectableFacet.getName().isEmpty())
        {
            checkbox.setText(selectableFacet.getName() + "  (" + selectableFacet.getCount() + ")");
        }
        else
        {
            checkbox.setText("<Name not specified>"  + "  (" + selectableFacet.getCount() + ")");
        }

        checkbox.setValue(selectableFacet.isSelected());

        checkbox.addClickHandler(this);
        add(checkbox);

    }

    @Override
    public void onClick(ClickEvent clickEvent)
    {
        // update document & POST new query
        selectableFacet.setSelected(checkbox.getValue());

        postCmd.execute();
    }
}