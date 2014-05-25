package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 *
 * @author noSqlOrBust
 */
public class NorthPanel extends FlowPanel {

    public NorthPanel() {
        Label label = new Label("The Walter Mitty Project");
        label.setStyleName("northPanelTitle");
        add(label);

        setStyleName("northPanel");

    }
}
