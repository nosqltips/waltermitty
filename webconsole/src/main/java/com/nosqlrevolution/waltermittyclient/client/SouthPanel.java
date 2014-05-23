package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.nosqlrevolution.waltermittyclient.model.SearchQuery;

/**
 *
 * @author cmorgan
 */
public class SouthPanel extends FlowPanel {

    private final Label label;

    public SouthPanel() {
        setStyleName("southPanel");

        label = new Label();
        label.setStyleName("southPanelLabel");
        add(label);
    }

    public void update(SearchQuery searchQuery) {
        StringBuilder stringBuilder = new StringBuilder();

        if (searchQuery.getMemberId() != null && !searchQuery.getMemberId().equalsIgnoreCase("null"))
        {
            stringBuilder.append("MemberId: " +searchQuery.getMemberId());
        }
        else
        {
            stringBuilder.append("MemberId: ");
        }

        stringBuilder.append(", TimeMillis: " + searchQuery.getTimeMillis())
                .append(", Total Results: " + searchQuery.getTotalResults())
                .append(", Available Results: " + searchQuery.getAvailableResults());

        label.setText(stringBuilder.toString());
    }

    public void setMessage(String message) {
        label.setText(message);
    }
}
