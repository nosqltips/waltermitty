package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.*;
import com.nosqlrevolution.waltermittyclient.model.FacetRequest;
import com.nosqlrevolution.waltermittyclient.model.SelectableFacet;

/**
 *
 * @author noSqlOrBust
 */
public class FacetPanel extends FlowPanel {

    private Command postCmd;
    private FacetRequest facetRequest;

    public FacetPanel(FacetRequest facetRequest, Command postCmd) {
        this.facetRequest = facetRequest;
        this.postCmd = postCmd;

        setStyleName("facetPanel");

        buildUI();
//        if (facetRequest.getSelectables() != null && facetRequest.getSelectables().size() > 0)
//        {
//            VerticalPanel verticalPanel = new VerticalPanel();
//            verticalPanel.setStyleName("selectablePanelInnerVerticalPanel");
//
//            add(verticalPanel);
//
//            for (SelectableFacet selectableFacet : facetRequest.getSelectables())
//            {
//                // Add selectables
//                SelectablesPanel selectablesPanel = new SelectablesPanel(selectableFacet, postCmd);
//                verticalPanel.add(selectablesPanel);
//            }
//        }
    }

    private void buildUI()
    {
        if (facetRequest.getSelectables() != null && facetRequest.getSelectables().size() > 0)
        {
            VerticalPanel verticalPanel = new VerticalPanel();
            verticalPanel.setStyleName("selectablePanelInnerVerticalPanel");

            add(verticalPanel);

            for (SelectableFacet selectableFacet : facetRequest.getSelectables())
            {
                // Add selectables
                SelectablesPanel selectablesPanel = new SelectablesPanel(selectableFacet, postCmd);
                verticalPanel.add(selectablesPanel);
            }
        }
    }
    public void reInitializeUI(FacetRequest facetRequest, Command postCmd)
    {
        // set facets field variable
        this.facetRequest = facetRequest;
        this.postCmd = postCmd;

        // clear panel of widgets
        this.clear();

        // build UI
        buildUI();
    }
}
