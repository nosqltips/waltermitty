package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 *
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author noSqlOrBust
 */
public class WalterMittyClient implements EntryPoint
{
    private SearchPanel searchPanel;
    private MltPanel mltPanel;

    /**
     * Create a remote service proxy to talk to the server-side Walter Mitty service.
     */

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        RootLayoutPanel rp = RootLayoutPanel.get();

//        searchPanel = new SearchPanel(Style.Unit.EM);
//        rp.add(searchPanel);

        mltPanel = new MltPanel(Style.Unit.EM);
        rp.add(mltPanel);
    }
}